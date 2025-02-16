package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.service.VolunteerService;
import com.example.volunteer_platform.shared_dto.*;
import com.example.volunteer_platform.server.exсeptions.*;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VolunteerControllerTests {

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    private final Volunteer testVolunteer = Volunteer.builder()
            .id(1L)
            .email("volunteer@example.com")
            .username("volunteerUser")
            .password("password123")
            .role(User.UserRole.VOLUNTEER)
            .build();

    @Mock
    private SessionUtils sessionUtils;

    @Test
    void createVolunteer_Success() {
        UserRegistrationDTO requestDTO = new UserRegistrationDTO("new@example.com", "newPass123", "newUser");
        UserResponseDTO responseDTO = new UserResponseDTO(2L, "newUser", "newPass123", "new@example.com", "VOLUNTEER");

        when(volunteerService.createVolunteer(any(), any(), any())).thenReturn(responseDTO);

        ResponseEntity<UserResponseDTO> response = volunteerController.createVolunteer(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody().getId());
        assertEquals("newUser", response.getBody().getUsername());
    }

    @Test
    void createVolunteer_EmailConflict() {
        when(volunteerService.createVolunteer(any(), any(), any()))
                .thenThrow(new EmailAlreadyExistsException("Email already registered"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                volunteerController.createVolunteer(new UserRegistrationDTO("exists@example.com", "password123", "userExists"))
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Email already registered", exception.getReason());
    }

    @Test
    void responseToEvent_Success() {
        EventResponseDTO responseDTO = createTestEventResponse();
        HttpServletRequest request = mock(HttpServletRequest.class); // Создаем мок-запрос

        when(SessionUtils.getUserFromSession(request)).thenReturn(testVolunteer);
        when(volunteerService.responseToEvent(10L, 1L)).thenReturn(responseDTO);

        ResponseEntity<EventResponseDTO> response = volunteerController.responseToEvent(10L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Community Event", Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void responseToEvent_Unauthorized() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(SessionUtils.getUserFromSession(any(HttpServletRequest.class)))
                .thenThrow(new SessionNotFoundException("No active session"));

        ResponseEntity<EventResponseDTO> response = volunteerController.responseToEvent(10L, request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    void responseToEvent_NotFound() {
        when(SessionUtils.getUserFromSession(any())).thenReturn(testVolunteer);

        when(volunteerService.responseToEvent(99L, 1L)).thenThrow(new EventNotExistsException("Event not found"));

        HttpServletRequest request = mock(HttpServletRequest.class);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                volunteerController.responseToEvent(99L, request)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Event not found", exception.getReason());
    }


    @Test
    void createVolunteer_InvalidEmailFormat() {
        when(volunteerService.createVolunteer(any(), any(), any()))
                .thenThrow(new InvalidEmailException("Invalid email format"));

        UserRegistrationDTO requestDTO = new UserRegistrationDTO("invalid-email", "password123", "userInvalid");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                volunteerController.createVolunteer(requestDTO)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Invalid email format", exception.getReason());
    }

    private EventResponseDTO createTestEventResponse() {
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
                new EventParticipantResponseDTO(1L, "Organizer", "org@example.com", "CUSTOMER"),
                List.of(new EventParticipantResponseDTO(
                        testVolunteer.getId(),
                        testVolunteer.getUsername(),
                        testVolunteer.getEmail(),
                        "VOLUNTEER"
                ))
        );
    }
}
