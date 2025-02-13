package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VerificationService verificationService;

    @Test
    void testIsValidEmail_ValidEmail_ReturnsTrue() {
        assertTrue(verificationService.isValidEmail("test@example.com"));
    }

    @Test
    void testIsValidEmail_InvalidEmail_ReturnsFalse() {
        assertFalse(verificationService.isValidEmail("invalid-email"));
    }

    @Test
    void testIsAvailableEmail_EmailNotExists_ReturnsTrue() {
        String email = "newuser@example.com";
        when(userRepository.existsUserByEmail(email)).thenReturn(false);

        assertTrue(verificationService.isAvailableEmail(email));

        verify(userRepository, times(1)).existsUserByEmail(email);
    }

    @Test
    void testIsAvailableEmail_EmailExists_ReturnsFalse() {
        String email = "existing@example.com";
        when(userRepository.existsUserByEmail(email)).thenReturn(true);

        assertFalse(verificationService.isAvailableEmail(email));

        verify(userRepository, times(1)).existsUserByEmail(email);
    }
}
