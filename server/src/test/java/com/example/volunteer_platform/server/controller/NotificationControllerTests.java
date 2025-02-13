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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    private MockedStatic<SessionUtils> mockedSessionUtils;

    private static final String USER_EMAIL = "user@example.com";
    private static final String USER_USERNAME = "User";
    private static final String USER_PASSWORD = "password";

    @BeforeEach
    void setUp() {
        Customer customer = createCustomer();
        mockedSessionUtils = Mockito.mockStatic(SessionUtils.class);
        mockedSessionUtils.when(() -> SessionUtils.getUserFromSession(any(HttpServletRequest.class)))
                .thenReturn(customer);
    }

    @Test
    void testGetVolunteerResponses_CustomerRole() throws Exception {
        List<VolunteerEventResponseDTO> responses = createVolunteerEventResponses();

        when(notificationService.getVolunteerResponses(any(User.class))).thenReturn(responses);

        mockMvc.perform(get("/notifications/received/responses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].volunteerId").value(101))
                .andExpect(jsonPath("$[0].volunteerName").value("Volunteer 1"))
                .andExpect(jsonPath("$[0].eventName").value("Event 1"))
                .andExpect(jsonPath("$[0].read").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].volunteerId").value(102))
                .andExpect(jsonPath("$[1].volunteerName").value("Volunteer 2"))
                .andExpect(jsonPath("$[1].eventName").value("Event 2"))
                .andExpect(jsonPath("$[1].read").value(false));

        verify(notificationService, times(1)).getVolunteerResponses(any(User.class));
    }

    @Test
    void testGetVolunteerResponses_NonCustomerRole() throws Exception {
        Volunteer volunteer = createVolunteer();

        mockedSessionUtils.when(() -> SessionUtils.getUserFromSession(any(HttpServletRequest.class)))
                .thenReturn(volunteer);

        mockMvc.perform(get("/notifications/received/responses"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Event feedback is only available to the customer."));
    }



    @Test
    void testGetReceivedMessages() throws Exception {
        List<MessageResponseDTO> messages = createMessageResponses();

        when(notificationService.getReceivedMessages(any(User.class))).thenReturn(messages);

        mockMvc.perform(get("/notifications/received/messages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].message").value("Message 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].message").value("Message 2"));

        verify(notificationService, times(1)).getReceivedMessages(any(User.class));
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
