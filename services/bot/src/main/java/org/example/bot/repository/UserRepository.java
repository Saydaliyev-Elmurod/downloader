package org.example.bot.repository;

import org.example.bot.domain.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends R2dbcRepository<UserEntity, UUID> {
    Mono<UserEntity> findByTelegramId(final Long telegramId);
}
