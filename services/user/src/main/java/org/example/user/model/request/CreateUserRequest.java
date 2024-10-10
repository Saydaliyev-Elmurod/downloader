package org.example.user.model.request;

public record CreateUserRequest(
        String email,
        String password
) {
}
