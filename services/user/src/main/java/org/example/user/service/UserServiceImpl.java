package org.example.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.jms.JmsPublisher;
import org.example.user.context.security.JwtService;
import org.example.common.exception.exp.NotFoundException;
import org.example.common.exception.exp.UserAlreadyExistException;
import org.example.user.domain.UserEntity;
import org.example.user.jms.model.SendEmailReply;
import org.example.user.mapper.UserMapper;
import org.example.user.model.TokenResponse;
import org.example.user.model.UserRequest;
import org.example.user.model.UserResponse;
import org.example.user.model.request.RequestLogin;
import org.example.user.repository.UserRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Log4j2
@AllArgsConstructor
public class UserServiceImpl implements UserService, ReactiveUserDetailsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JmsPublisher messagePublisher;

    @Override
    public Mono<UserResponse> create(final UserRequest request) {
        return userRepository.findByEmail(request.email())
                .flatMap(user -> Mono.error(new UserAlreadyExistException("User already exists")))
                .switchIfEmpty(
                        Mono.defer(() -> {
                            UserEntity entity = UserMapper.INSTANCE.toEntity(request);
                            entity.setRole("USER");
                            return userRepository.save(entity)
                                    .map(UserMapper.INSTANCE::toResponse)
                                    .flatMap(userResponse ->
                                            messagePublisher.publish(new SendEmailReply(request.email()))
                                                    .thenReturn(userResponse)
                                    );
                        })
                )
                .cast(UserResponse.class);
    }


    @Override
    public Mono<TokenResponse> login(final RequestLogin request) {
        return userRepository.findByPhone(request.phone())
                .switchIfEmpty(Mono.error(new NotFoundException("User not found")))
                .flatMap(userEntity -> {
                    String token = jwtService.generateToken(userEntity.getId());
                    return Mono.just(new TokenResponse(token));
                });
    }

    @Override
    public Mono<UserDetails> findByUsername(final String username) {
        return null;
    }
}
