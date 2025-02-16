package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.service.EventService;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventControllerTests {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Test
    void testGetAllEvents() {
        EventResponseDTO event1 = new EventResponseDTO(1L, "Event 1", "Description 1", "Location 1",
                LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 5, 2, null, null);
        EventResponseDTO event2 = new EventResponseDTO(2L, "Event 2", "Description 2", "Location 2",
                LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 10, 5, null, null);

        List<EventResponseDTO> eventsList = Arrays.asList(event1, event2);

        when(eventService.getAllEvents()).thenReturn(eventsList);

        ResponseEntity<List<EventResponseDTO>> response = eventController.getAllEvents();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Event 1", response.getBody().get(0).getName());
        assertEquals("Event 2", response.getBody().get(1).getName());
    }
}
