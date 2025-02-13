package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.service.EventService;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    void testGetAllEvents() throws Exception {
        EventResponseDTO event1 = new EventResponseDTO(1L, "Event 1", "Description 1", "Location 1", LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 5, 2, null, null);
        EventResponseDTO event2 = new EventResponseDTO(2L, "Event 2", "Description 2", "Location 2", LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 10, 5, null, null);

        List<EventResponseDTO> eventsList = Arrays.asList(event1, event2);

        when(eventService.getAllEvents()).thenReturn(eventsList);

        mockMvc.perform(get("/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Event 1"))
                .andExpect(jsonPath("$[1].name").value("Event 2"));
    }
}
