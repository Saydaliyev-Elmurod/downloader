package org.example.common.model.jms;

import java.util.UUID;

public record VideoResponse(
        UUID id,
        String hash,
        String title,
        String description,
        Integer duration,
        String thumbnail,
        String thumbnailFileId,

        String videoUrl,
        String videoFileId,

        String video144FileId,
        String video144FileUrl,
        String video240FileId,
        String video240FileUrl,


        String video360FileId,
        String video360FileUrl,

        String video480FileId,
        String video480FileUrl,

        String video720FileId,
        String video720FileUrl,

        String video1080FileId,
        String video1080FileUrl,

        String video1440FileId,
        String video1440FileUrl
) {
}
