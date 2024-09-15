package org.example.common.model;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String lastName,
        String firstName,
        String phone,
        String email,
        String role,
        Boolean isVerified,
        Instant createdDate,
        Instant lastModifiedDate
) {
}
