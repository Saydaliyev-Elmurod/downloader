package org.example.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.user.context.security.JwtService;
import org.example.user.domain.UserEntity;
import org.example.user.exception.NotFoundException;
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


    @Override
    public Mono<UserResponse> create(final UserRequest request) {
        return userRepository.save(UserMapper.INSTANCE.toEntity(request)).map(UserMapper.INSTANCE::toResponse);
    }

    @Override
    public Mono<TokenResponse> login(final RequestLogin request) {
        return userRepository.findByPhone(request.username())
                .switchIfEmpty(Mono.error(new NotFoundException("User not found")))
                .flatMap(userEntity -> {
                    String token = jwtService.generateToken(userEntity.getId());
                    return Mono.just(new TokenResponse(token));
                });
    }

    public UserEntity toEntity(UserRequest request) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(request.firstName());
        entity.setLastName(request.lastName());
        entity.setPhone(request.phone());
        return entity;
    }
 



    @Override
    public Mono<UserDetails> findByUsername(final String username) {
        return null;
    }
}
