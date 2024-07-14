package org.example.user.model;

import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@RedisHash(timeToLive = 60L)
public record SendEmail(String email, Instant time) {
}
