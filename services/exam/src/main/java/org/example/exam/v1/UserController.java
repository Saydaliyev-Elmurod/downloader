package org.example.exam.v1;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.common.model.UserResponse;
import org.example.exam.model.request.CreateUserRequest;
import org.example.exam.model.request.RequestLogin;
import org.example.exam.model.request.UserAssignPasswordRequest;
import org.example.exam.model.TokenResponse;
import org.example.exam.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("api/users/v1/users")
@Log4j2
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    public Mono<UserResponse> create(@RequestBody CreateUserRequest request) {
        log.debug("{}", request);
        return userService.create(request);
    }

    @PostMapping("/password")
    public Mono<UserResponse> create(@RequestBody UserAssignPasswordRequest request) {
        log.debug("Assign User password {}", request);
        return userService.assignPassword(request);
    }

    @GetMapping("/{id}")
    public Mono<UserResponse> findUser(@PathVariable UUID id) {
        log.debug("Find user who sent email  {}", id);
        return userService.findSentEmailUser(id);
    }

    @PostMapping("/login")
    public Mono<TokenResponse> login(@RequestBody RequestLogin request) {
        log.debug("{}", request);
        return userService.login(request);
    }
}