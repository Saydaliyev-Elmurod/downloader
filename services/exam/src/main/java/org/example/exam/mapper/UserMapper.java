package org.example.exam.mapper;

import org.example.common.model.UserResponse;
import org.example.exam.model.UserRequest;
import org.example.exam.domain.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = InstantMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(UserRequest request);


    UserResponse toResponse(UserEntity entity);
}
