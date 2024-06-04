package org.example.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.user.domain.UserEntity;
import org.example.user.model.UserRequest;
import org.example.user.model.UserResponse;
import org.example.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<UserEntity> listAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<UserEntity> findById(@PathVariable Integer id) {
        System.out.println("idi " + id);
        return userService.findById(id);
    }

    @PostMapping
    public Mono<UserResponse> create(@RequestBody UserRequest request) {
        log.debug("{}",request);
        return userService.create(request);
    }
    @PutMapping("/{id}")
    public Mono<UserResponse> update(@PathVariable Integer id,@RequestBody UserRequest request) {
        log.debug("{}",request);
        return userService.create(request);
    }
}