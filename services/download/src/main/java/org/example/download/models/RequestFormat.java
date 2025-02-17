package org.example.download.models;

public record RequestFormat(
        String url,
        String ext,
        Long filesize_approx
) {
}
