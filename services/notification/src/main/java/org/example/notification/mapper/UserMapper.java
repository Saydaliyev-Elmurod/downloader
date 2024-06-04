package org.example.notification.mapper;

import org.example.notification.domain.UserEntity;
import org.example.notification.model.UserRequest;
import org.example.notification.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = InstantMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(UserRequest request);

    UserResponse toResponse(UserEntity entity);
}
