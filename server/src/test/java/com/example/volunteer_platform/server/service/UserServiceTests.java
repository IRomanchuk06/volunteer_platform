package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exceptions.InvalidPasswordException;
import com.example.volunteer_platform.server.exceptions.UserNotFoundException;
import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.repository.UserRepository;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private UserService userService;

    private final Customer testUser = createTestUser();
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(testUser.getEmail());
        userResponseDTO.setUsername("newUser");
    }

    @Test
    void getUserByEmail_UserExists_ReturnsUser() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(testUser);
        Optional<User> result = userService.getUserByEmail("test@example.com");
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
        verify(userRepository, times(1)).findUserByEmail("test@example.com");
    }

    @Test
    void updateUser_ValidPassword_UpdatesUser() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(testUser);
        when(userMapper.toUserResponseDTO(any(User.class))).thenReturn(userResponseDTO);
        UserResponseDTO result = userService.updateUser("test@example.com", "newUser", "oldPassword", "newPassword");
        assertNotNull(result);
        assertEquals("newUser", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_InvalidPassword_ThrowsException() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(testUser);
        assertThrows(InvalidPasswordException.class, () ->
                userService.updateUser("test@example.com", "newUser", "wrongPassword", "newPassword"));
    }

    @Test
    void deleteUser_UserExists_DeletesUser() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(testUser);
        userService.deleteUser("test@example.com");
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    void sendMessage_ValidUsers_ReturnsMessageResponseDTO() {
        Customer recipient = new Customer();
        recipient.setEmail("recipient@example.com");
        recipient.setUsername("recipientUser");
        recipient.setPassword("password");

        MessageResponseDTO messageResponse = new MessageResponseDTO();
        messageResponse.setMessage("Message sent successfully");
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(testUser);
        when(userRepository.findUserByEmail("recipient@example.com")).thenReturn(recipient);
        when(messageService.sendMessage("Hello", testUser, recipient)).thenReturn(messageResponse);
        MessageResponseDTO result = userService.sendMessage("Hello", "test@example.com", "recipient@example.com");
        assertNotNull(result);
        assertEquals("Message sent successfully", result.getMessage());
        verify(messageService, times(1)).sendMessage("Hello", testUser, recipient);
    }

    @Test
    void getUserByUsername_UserExists_ReturnsUser() {
        when(userRepository.findUserByUsername("testUser")).thenReturn(testUser);
        Optional<User> result = userService.getUserByUsername("testUser");
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
        verify(userRepository, times(1)).findUserByUsername("testUser");
    }

    @Test
    void getUserByUsername_UserNotFound_ThrowsException() {
        when(userRepository.findUserByUsername("unknownUser")).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("unknownUser"));
    }

    @Test
    void updateUser_UserNotFound_ThrowsException() {
        when(userRepository.findUserByEmail("notfound@example.com")).thenReturn(null);
        assertThrows(UserNotFoundException.class, () ->
                userService.updateUser("notfound@example.com", "newUser", "oldPassword", "newPassword"));
    }

    @Test
    void deleteUser_UserNotFound_ThrowsException() {
        when(userRepository.findUserByEmail("notfound@example.com")).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser("notfound@example.com"));
    }

    private Customer createTestUser() {
        Customer testUser = new Customer();
        testUser.setEmail("test@example.com");
        testUser.setUsername("testUser");
        testUser.setPassword("oldPassword");

        return testUser;
    }
}
