package com.example.volunteer_platform.server.unit.controller;

import com.example.volunteer_platform.server.controller.VolunteerController;
import com.example.volunteer_platform.server.unit.tests_config.JacksonConfig;
import com.example.volunteer_platform.server.controller.advice.GlobalExceptionHandler;
import com.example.volunteer_platform.server.exceptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.server.exceptions.EventNotExistsException;
import com.example.volunteer_platform.server.exceptions.SessionNotFoundException;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.service.VolunteerService;
import com.example.volunteer_platform.shared_dto.EventParticipantResponseDTO;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.UserRegistrationDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
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
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VolunteerControllerTests {

    private MockMvc mockMvc;

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    private final JacksonConfig jacksonConfig = new JacksonConfig();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(volunteerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreateVolunteer_Success() throws Exception {
        UserRegistrationDTO requestDTO = new UserRegistrationDTO("new@example.com", "newPass123", "newUser");
        UserResponseDTO responseDTO = new UserResponseDTO(2L, "newUser", "newPass123", "new@example.com", "VOLUNTEER");

        when(volunteerService.createVolunteer(any(), any(), any())).thenReturn(responseDTO);

        mockMvc.perform(post("/volunteers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonConfig.objectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("new@example.com"))
                .andExpect(jsonPath("$.username").value("newUser"))
                .andExpect(jsonPath("$.role").value("VOLUNTEER"))
                .andExpect(jsonPath("$.id").value(2L));

        verify(volunteerService, times(1)).createVolunteer(any(), any(), any());
    }

    @Test
    void testCreateVolunteer_EmailConflict() throws Exception {
        when(volunteerService.createVolunteer(any(), any(), any()))
                .thenThrow(new EmailAlreadyExistsException("Email already registered"));

        UserRegistrationDTO requestDTO = new UserRegistrationDTO("exists@example.com", "password123", "userExists");

        mockMvc.perform(post("/volunteers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonConfig.objectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already registered"));

        verify(volunteerService, times(1)).createVolunteer(any(), any(), any());
    }

    @Test
    void testResponseToEvent_Success() throws Exception {
        EventResponseDTO responseDTO = createTestEventResponse();

        lenient().when(volunteerService.responseToEvent(10L, 0L)).thenReturn(responseDTO);

        mockMvc.perform(post("/volunteers/response/events/10")
                        .sessionAttr("currentUser", mock(Volunteer.class))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Community Event"))
                .andExpect(jsonPath("$.description").value("Help needed for community cleanup"));

        verify(volunteerService, times(1)).responseToEvent(anyLong(), anyLong());
    }

    @Test
    void testResponseToEvent_Unauthorized() throws Exception {
        when(volunteerService.responseToEvent(10L, 0L)).thenThrow(new SessionNotFoundException("No active session"));

        mockMvc.perform(post("/volunteers/response/events/10")
                        .sessionAttr("currentUser", mock(Volunteer.class))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("No active session"));

        verify(volunteerService, times(1)).responseToEvent(anyLong(), anyLong());
    }

    @Test
    void testResponseToEvent_NotFound() throws Exception {
        when(volunteerService.responseToEvent(99L, 0L)).thenThrow(new EventNotExistsException("Event not found"));

        mockMvc.perform(post("/volunteers/response/events/99")
                        .sessionAttr("currentUser", mock(Volunteer.class))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Event not found"));

        verify(volunteerService, times(1)).responseToEvent(anyLong(), anyLong());
    }

    private EventResponseDTO createTestEventResponse() {
        EventParticipantResponseDTO customer = new EventParticipantResponseDTO(1L, "customerUser", "customer@example.com", "CUSTOMER");

        EventParticipantResponseDTO volunteer = new EventParticipantResponseDTO(2L, "volunteerUser", "volunteer@example.com", "VOLUNTEER");

        java.util.List<EventParticipantResponseDTO> volunteerList = Collections.singletonList(volunteer);

        return new EventResponseDTO(
                100L,
                "Community Event",
                "Help needed for community cleanup",
                "Central Park",
                LocalDate.now(),
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                15,
                8,
                customer,
                volunteerList
        );
    }
}
