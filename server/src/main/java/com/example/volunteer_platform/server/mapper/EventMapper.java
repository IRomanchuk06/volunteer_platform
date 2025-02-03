package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.shared_dto.EventParticipantResponseDTO;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.server.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface EventMapper {
    @Mapping(target = "id", expression = "java(event.getId())")
    @Mapping(target = "name", expression = "java(event.getName())")
    @Mapping(target = "description", expression = "java(event.getDescription())")
    @Mapping(target = "location", expression = "java(event.getLocation())")
    @Mapping(target = "date", expression = "java(event.getDate())")
    @Mapping(target = "startTime", expression = "java(event.getStartTime())")
    @Mapping(target = "endTime", expression = "java(event.getEndTime())")
    @Mapping(target = "numOfRequiredVolunteers", expression = "java(event.getNumOfRequiredVolunteers())")
    @Mapping(target = "numOfRespondingVolunteers", expression = "java(event.getResponses().size())")
    @Mapping(target = "customer", expression = "java(mapEventParticipant(event.getCustomer()))")
    @Mapping(target = "volunteers", expression = "java(mapVolunteers(event.getVolunteers()))")
    EventResponseDTO toResponseDTO(Event event);

    default List<EventParticipantResponseDTO> mapVolunteers(Set<Volunteer> volunteers) {
        if (volunteers == null) {
            return Collections.emptyList();
        }
        return volunteers.stream()
                .map(this::mapEventParticipant)
                .toList();
    }

    default EventParticipantResponseDTO mapEventParticipant(User user) {
        return new EventParticipantResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}