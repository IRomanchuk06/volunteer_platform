package com.example.volunteer_platform.server.unit.mapper;

import com.example.volunteer_platform.server.mapper.EventMapper;
import com.example.volunteer_platform.server.model.*;
import com.example.volunteer_platform.server.unit.BaseUnitTests;
import com.example.volunteer_platform.shared_dto.EventParticipantResponseDTO;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class EventMapperTests extends BaseUnitTests {

    private final EventMapper eventMapper = Mappers.getMapper(EventMapper.class);

    @Test
    void shouldMapEventToResponseDTOWithAllFields() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setUsername("customer1");
        customer.setEmail("customer@test.com");
        customer.setRole(User.UserRole.CUSTOMER);

        Volunteer volunteer = new Volunteer();
        volunteer.setId(2L);
        volunteer.setUsername("volunteer1");
        volunteer.setEmail("volunteer@test.com");
        volunteer.setRole(User.UserRole.VOLUNTEER);

        Event event = Event.builder()
                .id(10L)
                .name("Test Event")
                .description("Test Description")
                .location("Test Location")
                .date(LocalDate.of(2023, 12, 31))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(18, 0))
                .numOfRequiredVolunteers(5)
                .customer(customer)
                .volunteers(Set.of(volunteer))
                .responses(List.of(new Response(), new Response())) // 2 responses
                .build();

        EventResponseDTO dto = eventMapper.toResponseDTO(event);

        assertAll(
                () -> assertEquals(10L, dto.getId()),
                () -> assertEquals("Test Event", dto.getName()),
                () -> assertEquals("Test Description", dto.getDescription()),
                () -> assertEquals("Test Location", dto.getLocation()),
                () -> assertEquals(LocalDate.of(2023, 12, 31), dto.getDate()),
                () -> assertEquals(LocalTime.of(10, 0), dto.getStartTime()),
                () -> assertEquals(LocalTime.of(18, 0), dto.getEndTime()),
                () -> assertEquals(5, dto.getNumOfRequiredVolunteers()),
                () -> assertEquals(2, dto.getNumOfRespondingVolunteers()),
                () -> assertEquals(1, dto.getVolunteers().size()),
                () -> assertParticipant(customer, dto.getCustomer())
        );
    }

    @Test
    void shouldHandleNullVolunteers() {
        Event event = new Event();
        event.setVolunteers(null);
        event.setCustomer(null);

        EventResponseDTO dto = eventMapper.toResponseDTO(event);

        assertAll(
                () -> assertTrue(dto.getVolunteers().isEmpty()),
                () -> assertNull(dto.getCustomer())
        );
    }

    @Test
    void shouldMapNullCustomerToNull() {
        Event event = new Event();
        event.setCustomer(null);

        EventResponseDTO dto = eventMapper.toResponseDTO(event);
        assertNull(dto.getCustomer());
    }

    private void assertParticipant(User expected, EventParticipantResponseDTO actual) {
        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getUsername(), actual.getUsername()),
                () -> assertEquals(expected.getEmail(), actual.getEmail()),
                () -> assertEquals(expected.getRole().name(), actual.getRole())
        );
    }
}