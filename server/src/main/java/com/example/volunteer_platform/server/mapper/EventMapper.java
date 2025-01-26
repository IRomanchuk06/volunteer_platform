package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.server.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventResponseDTO toResponseDTO(Event event);
}