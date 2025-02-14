package org.example.bot.repository;

import org.example.bot.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

//@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    UserEntity findByTelegramId(final Long telegramId);
}
