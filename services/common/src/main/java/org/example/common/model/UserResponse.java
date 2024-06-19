package org.example.common.model;

import java.time.Instant;

public record UserResponse(
        Integer id,
        String lastName,
        String firstName,
        String phone,
        Instant createdDate,
        Instant lastModifiedDate
) {
}
