package org.example.user.api.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.user.model.TokenResponse;
import org.example.user.model.UserRequest;
import org.example.user.model.UserResponse;
import org.example.user.model.request.RequestLogin;
import org.example.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/users/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    public Mono<UserResponse> create(@RequestBody UserRequest request) {
        log.debug("{}", request);
        return userService.create(request);
    }

    @PostMapping("/login")
    public Mono<TokenResponse> login(@RequestBody RequestLogin request) {
        log.debug("{}", request);
        return userService.login(request);
    }

}