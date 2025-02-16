package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.UserService;
import com.example.volunteer_platform.shared_dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserController userController;

    private Customer testCustomer = Customer.builder()
            .id(1L)
            .email("test@example.com")
            .username("testUser")
            .password("password123")
            .role(User.UserRole.CUSTOMER)
            .build();

    @Test
    void getUserByEmail_UserExists_ReturnsUser() {
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));
        ResponseEntity<User> response = userController.getUserByEmail("test@example.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testCustomer, response.getBody());
    }

    @Test
    void getUserByEmail_UserNotFound_ThrowsException() {
        when(userService.getUserByEmail("unknown@example.com")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userController.getUserByEmail("unknown@example.com"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found with email: unknown@example.com", exception.getReason());
    }

    @Test
    void getUserByUsername_UserExists_ReturnsUser() {
        when(userService.getUserByUsername("testUser")).thenReturn(Optional.of(testCustomer));
        ResponseEntity<User> response = userController.getUserByUsername("testUser");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testCustomer, response.getBody());
    }

    @Test
    void getUserByUsername_UserNotFound_ThrowsException() {
        when(userService.getUserByUsername("unknownUser")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userController.getUserByUsername("unknownUser"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found with username: unknownUser", exception.getReason());
    }

    @Test
    void updateUser_ValidRequest_ReturnsUpdatedUser() {
        UpdateUserDTO updateRequest = new UpdateUserDTO(
                "test@example.com",
                "newUsername",
                "newPassword",
                "oldPassword"
        );

        UserResponseDTO updatedUser = new UserResponseDTO(
                1L,
                "newUsername",
                "newPassword",
                "test@example.com",
                User.UserRole.CUSTOMER.toString()
        );

        when(userService.updateUser(
                "test@example.com",
                "newUsername",
                "oldPassword",
                "newPassword"
        )).thenReturn(updatedUser);

        ResponseEntity<UserResponseDTO> response = userController.updateUser(updateRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    void deleteUser_UserExists_ReturnsNoContent() {
        when(userService.deleteUser("test@example.com")).thenReturn(true);
        ResponseEntity<?> response = userController.deleteUser("test@example.com");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteUser_UserNotFound_ThrowsException() {
        when(userService.deleteUser("unknown@example.com")).thenReturn(false);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userController.deleteUser("unknown@example.com"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found with email: unknown@example.com", exception.getReason());
    }

    @Test
    void sendMessage_ValidRequest_ReturnsMessageResponse() {
        MessageRegistrationDTO messageRequest = new MessageRegistrationDTO("Hello!", "recipient@example.com");
        MessageResponseDTO responseDTO = new MessageResponseDTO(100L, "test@example.com", "recipient@example.com",
                "Hello!", "TEXT", LocalDateTime.of(2024, 2, 14, 12, 0), false);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(testCustomer);
        when(userService.sendMessage("Hello!", "recipient@example.com", "test@example.com")).thenReturn(responseDTO);
        ResponseEntity<MessageResponseDTO> response = userController.sendMessage(messageRequest, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }
}