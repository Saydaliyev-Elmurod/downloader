package org.example.user.mapper;

import org.example.user.domain.UserEntity;
import org.example.user.model.UserRequest;
import org.example.user.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = InstantMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(UserRequest request);

    UserResponse toResponse(UserEntity entity);
}
