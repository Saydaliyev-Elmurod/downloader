package org.example.common.model;

public record UserRequest(
        String lastName,
        String firstName,
        String phone
) {
}
