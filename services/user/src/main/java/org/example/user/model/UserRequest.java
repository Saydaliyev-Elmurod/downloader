package org.example.user.model;

public record UserRequest(
        String lastName,
        String firstName,
        String phone,
        String email
) {
}
