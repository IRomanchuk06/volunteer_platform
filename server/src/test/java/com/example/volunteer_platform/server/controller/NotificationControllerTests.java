package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.service.NotificationService;
import com.example.volunteer_platform.server.utils.SessionUtils;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerEventResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationControllerTests {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService notificationService;

    private MockedStatic<SessionUtils> mockedSessionUtils;

    private static final String USER_EMAIL = "user@example.com";
    private static final String USER_USERNAME = "User";
    private static final String USER_PASSWORD = "password";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Customer customer = createCustomer();
        mockedSessionUtils = Mockito.mockStatic(SessionUtils.class);
        mockedSessionUtils.when(() -> SessionUtils.getUserFromSession(any(HttpServletRequest.class)))
                .thenReturn(customer);
    }

    @Test
    void testGetVolunteerResponses_CustomerRole() {
        List<VolunteerEventResponseDTO> responses = createVolunteerEventResponses();
        when(notificationService.getVolunteerResponses(any(User.class))).thenReturn(responses);

        ResponseEntity<?> responseEntity = notificationController.getVolunteerResponses(mock(HttpServletRequest.class));

        assertEquals(200, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, ((List<?>) responseEntity.getBody()).size());
    }

    @Test
    void testGetVolunteerResponses_NonCustomerRole() {
        Volunteer volunteer = createVolunteer();
        mockedSessionUtils.when(() -> SessionUtils.getUserFromSession(any(HttpServletRequest.class)))
                .thenReturn(volunteer);

        ResponseEntity<?> responseEntity = notificationController.getVolunteerResponses(mock(HttpServletRequest.class));

        assertEquals(403, responseEntity.getStatusCode().value());
        assertEquals("Event feedback is only available to the customer.", responseEntity.getBody());
    }

    @Test
    void testGetReceivedMessages() {
        List<MessageResponseDTO> messages = createMessageResponses();
        when(notificationService.getReceivedMessages(any(User.class))).thenReturn(messages);

        ResponseEntity<?> responseEntity = notificationController.getReceivedMessages(mock(HttpServletRequest.class));

        assertEquals(200, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, ((List<?>) responseEntity.getBody()).size());
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setEmail(USER_EMAIL);
        customer.setPassword(USER_PASSWORD);
        customer.setUsername(USER_USERNAME);
        customer.setRole(User.UserRole.CUSTOMER);
        return customer;
    }

    private Volunteer createVolunteer() {
        Volunteer volunteer = new Volunteer();
        volunteer.setEmail(USER_EMAIL);
        volunteer.setPassword(USER_PASSWORD);
        volunteer.setUsername(USER_USERNAME);
        volunteer.setRole(User.UserRole.VOLUNTEER);
        return volunteer;
    }

    private List<VolunteerEventResponseDTO> createVolunteerEventResponses() {
        return List.of(
                new VolunteerEventResponseDTO(1L, 101L, "Volunteer 1", "Event 1", true, LocalDateTime.now()),
                new VolunteerEventResponseDTO(2L, 102L, "Volunteer 2", "Event 2", false, LocalDateTime.now())
        );
    }

    private List<MessageResponseDTO> createMessageResponses() {
        return List.of(
                new MessageResponseDTO(1L, "sender@example.com", "recipient@example.com", "Message 1", "info", LocalDateTime.now(), false),
                new MessageResponseDTO(2L, "sender@example.com", "recipient@example.com", "Message 2", "alert", LocalDateTime.now(), true)
        );
    }

    @AfterEach
    void tearDown() {
        if (mockedSessionUtils != null) {
            mockedSessionUtils.close();
        }
    }
}
