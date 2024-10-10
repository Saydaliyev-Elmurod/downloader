package org.example.user.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.example.common.util.CustomInstantDeserializer;
import org.example.common.util.CustomInstantSerializer;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record SignUpRedisModel(
        String email,
        UUID uuid,
        Integer code,
        @JsonSerialize(using = CustomInstantSerializer.class)
        @JsonDeserialize(using = CustomInstantDeserializer.class)
        Instant time
) implements Serializable {
}
