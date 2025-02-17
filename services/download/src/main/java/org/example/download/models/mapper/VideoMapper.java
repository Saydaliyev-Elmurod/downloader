package org.example.download.models.mapper;

import org.example.common.model.jms.VideoResponse;
import org.example.download.domain.VideoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = InstantMapper.class)
public abstract class VideoMapper {

    public static final VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);

    public abstract VideoResponse toResponse(final VideoEntity videoEntity);


}
