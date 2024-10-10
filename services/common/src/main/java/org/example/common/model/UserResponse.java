package org.example.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

//@JsonIgnoreProperties(ignoreUnknown = true)
public record UserResponse(
        UUID id,
        String lastName,
        String firstName,
        String phone,
        String email,
        String role,
        Boolean isVerified
) implements Serializable {
}
