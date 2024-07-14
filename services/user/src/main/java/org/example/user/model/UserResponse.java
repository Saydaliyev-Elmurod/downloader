package org.example.user.model;

import java.time.Instant;

public record UserResponse(
        Integer id,
        String lastName,
        String firstName,
        String phone,
        String email,
        Instant createdDate,
        Instant lastModifiedDate
) {
}
