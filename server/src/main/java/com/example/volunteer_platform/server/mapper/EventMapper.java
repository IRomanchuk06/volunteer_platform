package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.shared_dto.EventParticipantResponseDTO;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.server.model.Event;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface EventMapper {

    EventResponseDTO toResponseDTO(Event event);

    default List<EventParticipantResponseDTO> mapVolunteers(Set<Volunteer> volunteers) {
        if (volunteers == null) {
            return Collections.emptyList();
        }
        return volunteers.stream()
                .map(this::mapVolunteer)
                .toList();
    }

    default EventParticipantResponseDTO mapVolunteer(Volunteer volunteer) {
        return new EventParticipantResponseDTO(
                volunteer.getId(),
                volunteer.getUsername(),
                volunteer.getEmail(),
                volunteer.getRole()
        );
    }
}