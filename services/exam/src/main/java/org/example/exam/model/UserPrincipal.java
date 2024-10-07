package org.example.exam.model;

import org.example.common.model.UserResponse;

public record UserPrincipal(UserResponse user, String token) {}
