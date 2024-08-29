package org.example.common;

public record UserRequest(
        String lastName,
        String firstName,
        String phone
) {
}
