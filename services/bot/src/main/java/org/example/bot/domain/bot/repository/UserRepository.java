package org.example.bot.domain.bot.repository;

import org.example.bot.domain.bot.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

//@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    UserEntity findByTelegramId(final Long telegramId);
}
