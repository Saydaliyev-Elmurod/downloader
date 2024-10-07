package org.example.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.common.exception.exp.NotFoundException;
import org.example.user.mapper.SubjectMapper;
import org.example.user.model.UserPrincipal;
import org.example.user.model.request.SubjectRequest;
import org.example.user.model.response.SubjectResponse;
import org.example.user.repository.SubjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public Mono<SubjectResponse> create(final UserPrincipal userPrincipal, final SubjectRequest request) {
        log.info("Create subject {} {} ", userPrincipal, request);
        return subjectRepository.save(SubjectMapper.INSTANCE.toEntity(request, userPrincipal.user().id()))
                .map(SubjectMapper.INSTANCE::toResponse);
    }

    public Mono<SubjectResponse> update(final UUID id, final SubjectRequest request) {
        log.info("Update subject {} {} ", id, request);
        return subjectRepository.findByIdAndDeletedFalse(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Subject not found")))
                .flatMap(subject -> {
                    SubjectMapper.INSTANCE.update(subject, request);
                    return subjectRepository.save(subject).map(SubjectMapper.INSTANCE::toResponse);
                });
    }

    public Mono<SubjectResponse> findById(final UUID id) {
        log.info("Find subject {} ", id);
        return subjectRepository.findByIdAndDeletedFalse(id)
                .map(SubjectMapper.INSTANCE::toResponse);
    }

    public Flux<SubjectResponse> findAll() {
        log.info("Find subject ");
        return subjectRepository.findAllByDeletedFalse()
                .map(SubjectMapper.INSTANCE::toResponse);
    }

    public Mono<Page<SubjectResponse>> findAllPage(Pageable pageable) {
        log.info("Find subjects page");
        return subjectRepository.countAllByDeletedFalse()
                .flatMap(totalElements ->
                        subjectRepository.findAllByDeletedFalse(pageable)
                                .map(SubjectMapper.INSTANCE::toResponse)
                                .collectList()
                                .map(subjects -> new PageImpl<>(subjects, pageable, totalElements))
                );
    }


}
