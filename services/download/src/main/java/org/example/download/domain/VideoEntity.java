package org.example.download.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.download.util.Constant;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@Table(name = Constant.TABLE_VIDEO, schema = Constant.SCHEMA)
public class VideoEntity {
    @Id
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
