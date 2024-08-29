package org.example.user.repository;

import org.example.user.domain.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Integer> {

    Mono<UserEntity> findById(int id);

    Mono<UserEntity> findByEmail(final String email);

    Mono<UserEntity> findByPhone(String phone);
}