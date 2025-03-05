package com.example.volunteer_platform.server.unit.mapper;

import com.example.volunteer_platform.server.mapper.VolunteerResponseMapper;
import com.example.volunteer_platform.server.model.*;
import com.example.volunteer_platform.server.unit.BaseUnitTests;
import com.example.volunteer_platform.shared_dto.VolunteerEventResponseDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class VolunteerResponseMapperTests extends BaseUnitTests {

    private final VolunteerResponseMapper mapper = Mappers.getMapper(VolunteerResponseMapper.class);

    @Test
    void shouldMapResponseToDTOWithAllFields() {
        Volunteer volunteer = Volunteer.builder()
                .id(1L)
                .username("volunteerUser")
                .build();

        Event event = Event.builder()
                .name("Test Event")
                .build();

        Response response = Response.builder()
                .id(100L)
                .sender(volunteer)
                .event(event)
                .isRead(true)
                .createdAt(LocalDateTime.now())
                .build();

        VolunteerEventResponseDTO dto = mapper.toVolunteerResponseDTO(response);

        assertAll(
                () -> assertEquals(100L, dto.getId()),
                () -> assertEquals(1L, dto.getVolunteerId()),
                () -> assertEquals("volunteerUser", dto.getVolunteerName()),
                () -> assertEquals("Test Event", dto.getEventName()),
                () -> assertTrue(dto.isRead()),
                () -> assertEquals(response.getCreatedAt(), dto.getCreatedAt())
        );
    }

    @Test
    void shouldHandleNullFieldsGracefully() {
        Response response = Response.builder()
                .id(200L)
                .sender(null)
                .event(null)
                .build();

        VolunteerEventResponseDTO dto = mapper.toVolunteerResponseDTO(response);

        assertAll(
                () -> assertEquals(200L, dto.getId()),
                () -> assertNull(dto.getVolunteerId()),
                () -> assertNull(dto.getVolunteerName()),
                () -> assertNull(dto.getEventName())
        );
    }

    @Test
    void shouldFilterAndConvertNotificationList() {
        Response response1 = createTestResponse(1L);
        Response response2 = createTestResponse(2L);
        Notification otherNotification = new Message();

        List<Notification> notifications = List.of(response1, response2, otherNotification);

        List<VolunteerEventResponseDTO> result = mapper.toVolunteerResponseDTOList(notifications);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    void shouldReturnEmptyListForNullInput() {
        List<VolunteerEventResponseDTO> result = mapper.toVolunteerResponseDTOList(null);
        assertTrue(result.isEmpty());
    }

    private Response createTestResponse(Long id) {
        return Response.builder()
                .id(id)
                .sender(Volunteer.builder().id(id).username("user" + id).build())
                .event(Event.builder().name("Event " + id).build())
                .build();
    }
}