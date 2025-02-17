package org.example.common.model.jms;

public record SendInstagramVideoReply(
        Long telegramId,
        String url,
        String thumb,
        String hash
) {
}
