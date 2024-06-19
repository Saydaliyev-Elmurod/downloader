package org.example.user.service;

import org.example.user.model.TokenResponse;
import org.example.user.model.UserRequest;
import org.example.user.model.UserResponse;
import org.example.user.model.request.RequestLogin;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResponse> create(UserRequest request);

    Mono<TokenResponse> login(RequestLogin request);
}
