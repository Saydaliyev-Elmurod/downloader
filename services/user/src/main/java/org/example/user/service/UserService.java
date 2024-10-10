package org.example.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.common.exception.ErrorCodeEnum;
import org.example.common.exception.exp.AlreadyExistsException;
import org.example.common.exception.exp.InvalidArgumentException;
import org.example.common.exception.exp.NotFoundException;
import org.example.common.model.UserResponse;
import org.example.common.model.jms.SendEmailReply;
import org.example.common.util.CommonUtils;
import org.example.jms.JmsPublisher;
import org.example.user.context.security.JwtService;
import org.example.user.domain.UserEntity;
import org.example.user.mapper.UserMapper;
import org.example.user.model.SignUpRedisModel;
import org.example.user.model.TokenResponse;
import org.example.user.model.request.CreateUserRequest;
import org.example.user.model.request.RequestLogin;
import org.example.user.model.request.VerifyRequest;
import org.example.user.model.response.SignUpResponse;
import org.example.user.repository.UserRepository;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JmsPublisher messagePublisher;
    private final ReactiveRedisTemplate<String, SignUpRedisModel> userAuthRedisTemplate;
    private final ReactiveRedisTemplate<String, UserResponse> userRedisTemplate;

    public Mono<SignUpResponse> create(final CreateUserRequest request) {
        String email = request.email();
        // Find user from redis
        return userAuthRedisTemplate.opsForValue().get(email)
                .flatMap(existingData -> {
                    // If user exist return data
                    log.info("User login data exist redis {} ", existingData);
                    return Mono.just(new SignUpResponse(existingData.email(), existingData.time(), existingData.uuid()));
                })
                .switchIfEmpty(
                        // User not exist ,find from db
                        userRepository.findByEmail(email).flatMap(existUser -> {
                            if (Boolean.TRUE.equals(existUser.getIsVerified())) {
                                // user already exist with this email
                                return Mono.error(new AlreadyExistsException(ErrorCodeEnum.USER_ALREADY_EXIST.code, "User already exists"));
                            } else {
                                Integer code = CommonUtils.generateRandomDigits();
                                log.info("Generated code : {} ", code);
                                // save redis and publish code to user
                                SignUpRedisModel signUpRedisModel = new SignUpRedisModel(existUser.getEmail(), UUID.randomUUID(), code, Instant.now());
                                return userAuthRedisTemplate.opsForValue().set(existUser.getEmail(), signUpRedisModel, Duration.ofMinutes(3)) // TTL to 3 minutes
                                        .then(messagePublisher.publish(new SendEmailReply(email, code)))
                                        .thenReturn(new SignUpResponse(signUpRedisModel.email(), signUpRedisModel.time(), signUpRedisModel.uuid()));
                            }
                        }).switchIfEmpty(
                                Mono.defer(() -> {
                                    // Create new user
                                    UserEntity entity = new UserEntity();
                                    entity.setEmail(request.email());
                                    entity.setIsVerified(Boolean.FALSE);
                                    entity.setRole("USER");
                                    entity.setPassword(BCrypt.hashpw(request.password(), BCrypt.gensalt()));
                                    Integer code = CommonUtils.generateRandomDigits();
                                    log.info("Generated code : {} ", code);

                                    // save and send code
                                    return userRepository.save(entity)
                                            .flatMap(savedUser ->
                                                    userAuthRedisTemplate.opsForValue().set(savedUser.getEmail(), new SignUpRedisModel(savedUser.getEmail(), UUID.randomUUID(), code, Instant.now()), Duration.ofMinutes(3)) // TTL to 3 minutes
                                                            .then(messagePublisher.publish(new SendEmailReply(email, code)))
                                                            .thenReturn(new SignUpResponse(savedUser.getEmail(), Instant.now(), UUID.randomUUID())));
                                })
                        )
                );
    }


    public Mono<TokenResponse> login(final RequestLogin request) {
        return userRepository.findByEmail(request.email()).switchIfEmpty(Mono.error(new NotFoundException("User not found")))
                .flatMap(userEntity -> {
                    String token = jwtService.generateToken(userEntity.getId());
                    return Mono.just(new TokenResponse(token));
                });
    }


    public Mono<UserResponse> verify(final VerifyRequest request) {
        return userAuthRedisTemplate.opsForValue().get(request.email())
                .flatMap(user -> {
                    if (request.uuid().equals(user.uuid()) && request.code().equals(user.code())) {
                        return userRepository.findByIdAndDeletedFalse(request.userId())
                                .flatMap(userEntity -> {
                                    userEntity.setIsVerified(Boolean.TRUE);
                                    return userRepository.save(userEntity)
                                            .map(UserMapper.INSTANCE::toResponse);
                                });
                    } else {
                        return Mono.error(new InvalidArgumentException("UUID or code not equal"));
                    }
                })
                .switchIfEmpty(Mono.error(new InvalidArgumentException("Time already expired")));
    }


    public Mono<UserResponse> findSentEmailUser(final UUID id) {
        return userRedisTemplate.opsForValue().get(id.toString())
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.defer(() ->
                        userRepository.findById(id)
                                .flatMap(user -> Mono.just(UserMapper.INSTANCE.toResponse(user)))
                                .switchIfEmpty(Mono.error(new NotFoundException("User not found")))
                ));
    }

}
