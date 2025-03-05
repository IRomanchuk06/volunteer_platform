package com.example.volunteer_platform.server.unit.controller;

import com.example.volunteer_platform.server.controller.EventController;
import com.example.volunteer_platform.server.service.EventService;
import com.example.volunteer_platform.server.unit.BaseUnitTests;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EventControllerTests extends BaseUnitTests {

    private MockMvc mockMvc;

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(eventController)
                .build();
    }

    @Test
    void testGetAllEvents() throws Exception {
        EventResponseDTO event1 = new EventResponseDTO(1L, "Event 1", "Description 1", "Location 1",
                LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 5, 2, null, null);
        EventResponseDTO event2 = new EventResponseDTO(2L, "Event 2", "Description 2", "Location 2",
                LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 10, 5, null, null);

        List<EventResponseDTO> eventsList = Arrays.asList(event1, event2);

        when(eventService.getAllEvents()).thenReturn(eventsList);

        mockMvc.perform(get("/events/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Event 1"))
                .andExpect(jsonPath("$[1].name").value("Event 2"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[1].description").value("Description 2"));
    }
}
