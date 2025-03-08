package org.example.bot.domain.bot.model;

public record RequestFormat(
        String url,
        String ext,
        Long filesize_approx
) {
}
