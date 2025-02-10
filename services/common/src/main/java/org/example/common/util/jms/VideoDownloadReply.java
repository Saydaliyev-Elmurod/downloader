package org.example.common.util.jms;

import java.time.Instant;

public record VideoDownloadReply(
        Long telegramId,
        String link,
        Instant time
) {
}
