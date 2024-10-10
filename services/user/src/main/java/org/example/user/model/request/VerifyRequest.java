package org.example.user.model.request;

import org.immutables.builder.Builder;
import org.springframework.util.Assert;

import java.util.UUID;

public record VerifyRequest(
        UUID userId,
        String email,
        Integer code,
        UUID uuid
) {

    @Builder.Constructor
    public VerifyRequest {
        Assert.notNull(userId, "user required");
        Assert.notNull(code, "code required");
    }
}
