package org.example.user.mapper;

import org.example.common.model.UserResponse;
import org.example.user.domain.SubjectEntity;
import org.example.user.domain.UserEntity;
import org.example.user.model.UserRequest;
import org.example.user.model.request.SubjectRequest;
import org.example.user.model.response.SubjectResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = InstantMapper.class)
public interface SubjectMapper {

    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    @Mapping(target = "nameUz", source = "request.name.uz")
    @Mapping(target = "nameRu", source = "request.name.ru")
    @Mapping(target = "nameEn", source = "request.name.en")
    @Mapping(target = "userId", source = "userId")
    SubjectEntity toEntity(SubjectRequest request, UUID userId);

    @Mapping(target = "nameUz", source = "request.name.uz")
    @Mapping(target = "nameRu", source = "request.name.ru")
    @Mapping(target = "nameEn", source = "request.name.en")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SubjectEntity update(final @MappingTarget SubjectEntity entity, final SubjectRequest request);

    @Mapping(source = "nameUz", target = "name.uz")
    @Mapping(source = "nameRu", target = "name.ru")
    @Mapping(source = "nameEn", target = "name.en")
    SubjectResponse toResponse(SubjectEntity entity);
}
