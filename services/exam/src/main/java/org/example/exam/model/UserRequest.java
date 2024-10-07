package org.example.exam.model;

public record UserRequest(
        String lastName,
        String firstName,
        String phone,
        String email,
        String password
) {
}
