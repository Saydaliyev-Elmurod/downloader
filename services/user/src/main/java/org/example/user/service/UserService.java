package org.example.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.common.exception.ErrorCodeEnum;
import org.example.common.exception.exp.AlreadyExistsException;
import org.example.common.exception.exp.NotFoundException;
import org.example.common.model.UserResponse;
import org.example.common.model.jms.SendEmailReply;
import org.example.jms.JmsPublisher;
import org.example.user.context.security.JwtService;
import org.example.user.domain.UserEntity;
import org.example.user.mapper.UserMapper;
import org.example.user.model.TokenResponse;
import org.example.user.model.request.CreateUserRequest;
import org.example.user.model.request.RequestLogin;
import org.example.user.model.request.UserAssignPasswordRequest;
import org.example.user.repository.UserRepository;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JmsPublisher messagePublisher;
    private final ReactiveRedisTemplate<String, UserResponse> userAuthRedisTemplate;

    public Mono<UserResponse> create(final CreateUserRequest request) {
        String email = request.email();
        // Redis'dan foydalanuvchi ma'lumotlarini olish
        return userAuthRedisTemplate.opsForValue().get(email)
                .flatMap(Mono::just)
                .switchIfEmpty(
                        // Agar Redis'da foydalanuvchi mavjud bo'lmasa, ma'lumotlar bazasidan olish
                        Mono.defer(() -> userRepository.findByEmail(email)
                                .flatMap(existUser -> {
                                    if (Boolean.TRUE.equals(existUser.getIsVerified())) {
                                        return Mono.error(new AlreadyExistsException(ErrorCodeEnum.USER_ALREADY_EXIST.code, "User already exists"));
                                    } else {
                                        UserResponse userResponse = UserMapper.INSTANCE.toResponse(existUser);
                                        // Redis'ga saqlash
                                        return userAuthRedisTemplate.opsForValue()
                                                .set(email, userResponse, Duration.ofMinutes(3)) // ttl to 3 minutes
                                                .then(messagePublisher.publish(new SendEmailReply(email)))
                                                .thenReturn(userResponse);
                                    }
                                }).switchIfEmpty(Mono.defer(() -> {
                                    UserEntity entity = new UserEntity();
                                    entity.setEmail(request.email());
                                    entity.setIsVerified(Boolean.FALSE);
                                    entity.setRole("USER");
                                    return userRepository.save(entity).map(UserMapper.INSTANCE::toResponse);
                                }))

                        )
                )
                .cast(UserResponse.class);
    }


    public Mono<TokenResponse> login(final RequestLogin request) {
        return userRepository.findByPhone(request.email())
                .switchIfEmpty(Mono.error(new NotFoundException("User not found")))
                .flatMap(userEntity -> {
                    String token = jwtService.generateToken(userEntity.getId());
                    return Mono.just(new TokenResponse(token));
                });
    }


    public Mono<UserResponse> assignPassword(final UserAssignPasswordRequest request) {
        return userRepository.findById(request.userId())
                .flatMap(user -> {
                    user.setPassword(BCrypt.hashpw(request.password(), BCrypt.gensalt()));
                    return userRepository.save(user)
                            .map(UserMapper.INSTANCE::toResponse);
                })
                .switchIfEmpty(Mono.error(new NotFoundException("User not found")));
    }

    public Mono<UserResponse> findSentEmailUser(final UUID id) {
        return userAuthRedisTemplate.opsForValue().get(id).flatMap(
                user ->
                        userRepository.findById(id).flatMap(userInDb -> {
                            userInDb.setIsVerified(Boolean.TRUE);
                            return userRepository.save(userInDb).map(UserMapper.INSTANCE::toResponse);
                        }).switchIfEmpty(Mono.error(new NotFoundException(ErrorCodeEnum.USER_NOT_FOUND.code, "User not found")))

        ).switchIfEmpty(Mono.defer(() -> userRepository.findById(id).flatMap(user -> Mono.just(UserMapper.INSTANCE.toResponse(user))
        ).switchIfEmpty(Mono.error(new NotFoundException("User not found")))));
    }
}
