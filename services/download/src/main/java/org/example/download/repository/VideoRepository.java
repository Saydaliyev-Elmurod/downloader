package org.example.download.repository;

import org.example.download.domain.VideoEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface VideoRepository extends R2dbcRepository<VideoEntity, UUID> {

    Mono<VideoEntity> findByHash(String hash);
}
