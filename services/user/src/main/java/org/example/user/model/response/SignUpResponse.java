package org.example.user.model.response;


import java.time.Instant;
import java.util.UUID;

public record SignUpResponse(
        String email,
        Instant time,
        UUID uuid
) {
}
