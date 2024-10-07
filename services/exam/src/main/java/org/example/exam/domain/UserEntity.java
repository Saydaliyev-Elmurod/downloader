package org.example.exam.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.exam.util.Constants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
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
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Boolean isVerified;
    private String password;
    private String role;
    @CreatedDate
    Instant createdDate;
    @LastModifiedDate
    Instant lastModifiedDate;
    @Version
    Integer version;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}