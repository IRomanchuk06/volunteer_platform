package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.UserService;
import com.example.volunteer_platform.shared_dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.example.volunteer_platform.server.utils.SessionUtils.getUserFromSession;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private UserController userController;

    private Customer testUser;

    @BeforeEach
    void setUp() {
        testUser = Customer.builder()
                .id(1L)
                .email("test@example.com")
                .username("testUser")
                .password("password123")
                .role(User.UserRole.CUSTOMER)
                .build();
    }

    @Test
    void sendMessage_ValidRequest_ReturnsMessageResponse() {
        MessageRegistrationDTO messageRequest = new MessageRegistrationDTO("Hello!", "recipient@example.com");

        MessageResponseDTO responseDTO = new MessageResponseDTO(
                100L,
                "test@example.com",
                "recipient@example.com",
                "Hello!",
                "TEXT",
                LocalDateTime.of(2024, 2, 14, 12, 0),
                false
        );

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(testUser);
        when(getUserFromSession(request)).thenReturn(testUser);

        lenient().when(userService.sendMessage(anyString(), anyString(), anyString()))
                .thenReturn(responseDTO);

        ResponseEntity<MessageResponseDTO> response = userController.sendMessage(messageRequest, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        MessageResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);

        assertEquals(100L, responseBody.getId());
        assertEquals("test@example.com", responseBody.getSenderEmail());
        assertEquals("recipient@example.com", responseBody.getRecipientEmail());
        assertEquals("Hello!", responseBody.getMessage());
        assertEquals("TEXT", responseBody.getType());
        assertEquals(LocalDateTime.of(2024, 2, 14, 12, 0), responseBody.getCreatedAt());
        assertFalse(responseBody.isRead());
    }
}
