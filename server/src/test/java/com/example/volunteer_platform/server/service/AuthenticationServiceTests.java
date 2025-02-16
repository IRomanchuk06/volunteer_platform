package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exсeptions.AuthenticationException;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testAuthenticate_Success() {
        String email = "test@example.com";
        String password = "password123";
        User user = new Volunteer();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findUserByEmail(email)).thenReturn(user);

        User authenticatedUser = authenticationService.authenticate(email, password);

        assertNotNull(authenticatedUser);
        assertEquals(email, authenticatedUser.getEmail());
        assertEquals(password, authenticatedUser.getPassword());
    }

    @Test
    void testAuthenticate_UserNotFound() {
        String email = "test@example.com";
        String password = "password123";

        when(userRepository.findUserByEmail(email)).thenReturn(null);

        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->
                authenticationService.authenticate(email, password));

        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    void testAuthenticate_InvalidPassword() {
        String email = "test@example.com";
        String password = "password123";
        User user = new Volunteer();
        user.setEmail(email);
        user.setPassword("wrongpassword");

        when(userRepository.findUserByEmail(email)).thenReturn(user);

        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->
                authenticationService.authenticate(email, password));

        assertEquals("Invalid email or password", exception.getMessage());
    }
}
