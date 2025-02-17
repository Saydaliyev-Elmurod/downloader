package org.example.bot.domain.bot.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.bot.domain.bot.util.Constants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@Table(name = Constants.TABLE_VIDEO, schema = Constants.SCHEMA)
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String hash;

    private String title;
    private String description;
    private Integer duration;

    private String thumbnail;
    private String thumbnailFileId;

    private String videoUrl;
    private String videoFileId;

    private String video144FileId;
    private String video144FileUrl;
    private String video240FileId;
    private String video240FileUrl;


    private String video360FileId;
    private String video360FileUrl;

    private String video480FileId;
    private String video480FileUrl;

    private String video720FileId;
    private String video720FileUrl;

    private String video1080FileId;
    private String video1080FileUrl;

    private String video1440FileId;
    private String video1440FileUrl;

    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
}
