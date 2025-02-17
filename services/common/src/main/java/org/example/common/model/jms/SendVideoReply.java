package org.example.common.model.jms;

public record SendVideoReply(
        Long telegramId,
        VideoResponse video
) {
}
