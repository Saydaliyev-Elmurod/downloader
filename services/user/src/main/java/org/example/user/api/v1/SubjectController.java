package org.example.user.api.v1;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.user.model.UserPrincipal;
import org.example.user.model.request.SubjectRequest;
import org.example.user.model.response.SubjectResponse;
import org.example.user.service.SubjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("api/users/v1/subject")
@Log4j2
@AllArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public Mono<SubjectResponse> create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody SubjectRequest request) {
        return subjectService.create(userPrincipal, request);
    }

    @PutMapping("/{id}")
    public Mono<SubjectResponse> update(@PathVariable UUID id, @RequestBody SubjectRequest request) {
        return subjectService.update(id, request);
    }

    @GetMapping("/{id}")
    public Mono<SubjectResponse> findById(@PathVariable UUID id) {
        return subjectService.findById(id);
    }

    @GetMapping("/all")
    public Flux<SubjectResponse> findAll() {
        return subjectService.findAll();
    }

    @GetMapping
    public Mono<Page<SubjectResponse>> findAllPage(Pageable pageable) {
        return subjectService.findAllPage(pageable);
    }
}
