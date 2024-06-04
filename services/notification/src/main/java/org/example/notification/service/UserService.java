package org.example.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notification.domain.UserEntity;
import org.example.notification.mapper.UserMapper;
import org.example.notification.model.UserRequest;
import org.example.notification.model.UserResponse;
import org.example.notification.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Mono<UserEntity> findById(int id) {
        return userRepository.findById(id);
//                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")))
//                .switchIfEmpty(Mono.error(new NumberFormatException("NOt found")))
//                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Not found")));
    }

    public Mono<UserResponse> create(final UserRequest request) {
        log.debug("Create user : [{}] ", request);
        return Mono.just(request).map(UserMapper.INSTANCE::toEntity).flatMap(userRepository::save).map(UserMapper.INSTANCE::toResponse);
    }
}
