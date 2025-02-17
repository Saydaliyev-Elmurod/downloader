package org.example.download.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record VideoInfo(
        String id,
        String title,
        Double duration,
        String thumbnail,
        @JsonProperty("requested_formats")
        List<RequestFormat> formats,
        Long filesize_approx
) {
}
