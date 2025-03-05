package com.example.volunteer_platform.server.unit.service;

import com.example.volunteer_platform.server.exceptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.server.exceptions.InvalidEmailException;
import com.example.volunteer_platform.server.exceptions.UserNotExistsException;
import com.example.volunteer_platform.server.exceptions.VolunteerAlreadyParticipatingException;
import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.repository.UserRepository;
import com.example.volunteer_platform.server.service.EventService;
import com.example.volunteer_platform.server.service.MessageService;
import com.example.volunteer_platform.server.service.VolunteerService;
import com.example.volunteer_platform.server.unit.BaseUnitTests;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VolunteerServiceTests extends BaseUnitTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventService eventService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MessageService messageService;

    private VolunteerService volunteerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        volunteerService = new VolunteerService(userRepository, eventService, userMapper, messageService);
    }

    @Test
    void testCreateVolunteer_Success() {
        String email = "volunteer@example.com";
        String password = "password";
        String username = "volunteerUser";

        Volunteer volunteer = new Volunteer();
        volunteer.setEmail(email);
        volunteer.setPassword(password);
        volunteer.setUsername(username);
        volunteer.setRole(User.UserRole.VOLUNTEER);

        when(userRepository.findUserByEmail(email)).thenReturn(null);
        when(userMapper.toUserResponseDTO(volunteer)).thenReturn(new UserResponseDTO(1L, username, password, email, "VOLUNTEER"));

        UserResponseDTO response = volunteerService.createVolunteer(email, password, username);

        assertNotNull(response);
        assertEquals(email, response.getEmail());
        assertEquals(username, response.getUsername());
        assertEquals("VOLUNTEER", response.getRole());

        verify(userRepository, times(1)).save(any(Volunteer.class));
    }

    @Test
    void testCreateVolunteer_EmailAlreadyExists() {
        String email = "volunteer@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(new Volunteer());

        assertThrows(EmailAlreadyExistsException.class, () -> {
            volunteerService.createVolunteer(email, "password", "volunteerUser");
        });

        verify(userRepository, times(0)).save(any(Volunteer.class));
    }

    @Test
    void testCreateVolunteer_InvalidEmail() {
        String email = "invalid-email";
        assertThrows(InvalidEmailException.class, () -> {
            volunteerService.createVolunteer(email, "password", "volunteerUser");
        });

        verify(userRepository, times(0)).save(any(Volunteer.class));
    }

    @Test
    void testResponseToEvent_Success() {
        Long eventId = 1L;
        Long userId = 2L;
        Volunteer volunteer = new Volunteer();
        volunteer.setId(userId);

        when(userRepository.existsByEventIdAndVolunteerId(eventId, userId)).thenReturn(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(volunteer));

        EventResponseDTO eventResponseDTO = new EventResponseDTO();
        when(eventService.responseToEvent(eventId, volunteer)).thenReturn(eventResponseDTO);

        EventResponseDTO response = volunteerService.responseToEvent(eventId, userId);

        assertNotNull(response);
        verify(eventService, times(1)).responseToEvent(eventId, volunteer);
    }

    @Test
    void testResponseToEvent_VolunteerAlreadyParticipating() {
        Long eventId = 1L;
        Long userId = 2L;
        Volunteer volunteer = new Volunteer();
        volunteer.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(volunteer));
        when(userRepository.existsByEventIdAndVolunteerId(eventId, userId)).thenReturn(true);

        assertThrows(VolunteerAlreadyParticipatingException.class, () -> {
            volunteerService.responseToEvent(eventId, userId);
        });

        verify(eventService, times(0)).responseToEvent(anyLong(), any());
    }


    @Test
    void testResponseToEvent_UserNotExists() {
        Long eventId = 1L;
        Long userId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotExistsException.class, () -> {
            volunteerService.responseToEvent(eventId, userId);
        });

        verify(eventService, times(0)).responseToEvent(anyLong(), any());
    }
}
