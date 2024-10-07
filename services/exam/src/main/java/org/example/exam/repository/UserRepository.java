package org.example.exam.repository;

import org.example.exam.domain.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Integer> {

    Mono<UserEntity> findById(final UUID id);

    Mono<UserEntity> findByEmail(final String email);

    Mono<UserEntity> findByPhone(final String phone);
}