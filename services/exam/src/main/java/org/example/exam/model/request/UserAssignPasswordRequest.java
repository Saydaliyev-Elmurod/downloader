package org.example.exam.model.request;

import org.immutables.builder.Builder;
import org.springframework.util.Assert;

import java.util.UUID;

public record UserAssignPasswordRequest(
        UUID userId,
        String password
) {

    @Builder.Constructor
    public UserAssignPasswordRequest {
        Assert.notNull(userId, "user required");
        Assert.notNull(password, "password required");
    }
}
