package com.example.volunteer_platform.server.unit.controller;

import com.example.volunteer_platform.server.controller.NotificationController;
import com.example.volunteer_platform.server.controller.advice.GlobalExceptionHandler;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.NotificationService;
import com.example.volunteer_platform.server.unit.BaseUnitTests;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerEventResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTests extends BaseUnitTests {

    private MockMvc mockMvc;

    @Mock
    private HttpServletRequest request;

    @Mock
    private NotificationService notificationService;

    private final Customer customer = createCustomer();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new NotificationController(notificationService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetReceivedMessages_UserNotFound() throws Exception {
        mockMvc.perform(get("/notifications/received/messages"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("No active session found"));
    }

    @Test
    public void testGetReceivedMessages() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("currentUser", customer);
        request.setSession(session);

        List<MessageResponseDTO> messages = createMessageResponses();
        when(notificationService.getReceivedMessages(customer)).thenReturn(messages);

        mockMvc.perform(get("/notifications/received/messages")
                        .session((MockHttpSession) Objects.requireNonNull(request.getSession())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].message", is("Message 1")))
                .andExpect(jsonPath("$[1].message", is("Message 2")));
    }

    @Test
    void testGetVolunteerResponses_CustomerRole() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("currentUser", customer);
        request.setSession(session);

        List<VolunteerEventResponseDTO> responses = createVolunteerEventResponses();
        when(notificationService.getVolunteerResponses(customer)).thenReturn(responses);

        mockMvc.perform(get("/notifications/received/responses")
                        .session((MockHttpSession) Objects.requireNonNull(request.getSession())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].eventName", is("Event 1")))
                .andExpect(jsonPath("$[1].eventName", is("Event 2")));
    }

    @Test
    void testGetVolunteerResponses_NonCustomerRole() throws Exception {
        User volunteer = customer;
        volunteer.setRole(User.UserRole.VOLUNTEER);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("currentUser", volunteer);
        request.setSession(session);

        mockMvc.perform(get("/notifications/received/responses")
                        .session((MockHttpSession) Objects.requireNonNull(request.getSession())))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("Event feedback is only available to the customer.")));
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setEmail("user@example.com");
        customer.setPassword("password");
        customer.setUsername("User");
        customer.setRole(User.UserRole.CUSTOMER);
        return customer;
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
}
