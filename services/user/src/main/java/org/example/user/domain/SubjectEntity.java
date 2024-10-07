package org.example.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.user.util.Constants;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = Constants.TABLE_SUBJECT, schema = Constants.SCHEMA)
public class SubjectEntity {
    @Id
    private UUID id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private UUID userId;
    Boolean deleted;
    @CreatedDate
    Instant createdDate;
    @LastModifiedDate
    Instant lastModifiedDate;
}