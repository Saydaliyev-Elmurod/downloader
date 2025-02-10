package org.example.bot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.bot.util.Constants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = Constants.TABLE_USER, schema = Constants.SCHEMA)
public class UserEntity {
    @Id
    private UUID id;
    private Long telegramId;
    private String firstName;
    private String lastName;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant lastModifiedDate;
}
