package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.Response;
import com.example.volunteer_platform.shared_dto.VolunteerEventResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface VolunteerResponseMapper {
    @Mapping(target = "id", expression = "java(response.getId())")
    @Mapping(target = "volunteerId", expression = "java(response.getSender() != null ? response.getSender().getId() : null)")
    @Mapping(target = "volunteerName", expression = "java(response.getSender() != null ? response.getSender().getUsername() : null)")
    @Mapping(target = "eventName", expression = "java(response.getEvent() != null ? response.getEvent().getName() : null)")
    @Mapping(target = "read", expression = "java(response.isRead())")
    @Mapping(target = "createdAt", expression = "java(response.getCreatedAt())")
    VolunteerEventResponseDTO toVolunteerResponseDTO(Response response);

    default List<VolunteerEventResponseDTO> toVolunteerResponseDTOList(List<Notification> notifications) {
        if (notifications == null) {
            return Collections.emptyList();
        }
        List<Response> responses = notifications.stream()
                .filter(Response.class::isInstance)
                .map(Response.class::cast)
                .toList();
        return responses.stream().map(this::toVolunteerResponseDTO).collect(Collectors.toList());
    }
}