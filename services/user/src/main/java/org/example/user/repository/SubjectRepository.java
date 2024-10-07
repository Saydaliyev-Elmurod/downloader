package org.example.user.repository;

import org.example.user.domain.SubjectEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SubjectRepository extends R2dbcRepository<SubjectEntity, UUID> {
    Mono<SubjectEntity> findByIdAndDeletedFalse(final UUID id);

    Flux<SubjectEntity> findAllByDeletedFalse();

    Flux<SubjectEntity> findAllByDeletedFalse(Pageable pageable);

    Mono<Long> countAllByDeletedFalse();
}
