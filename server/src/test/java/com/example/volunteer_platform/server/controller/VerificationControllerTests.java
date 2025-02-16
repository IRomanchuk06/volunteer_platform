package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.service.VerificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationControllerTests {

    @Mock
    private VerificationService verificationService;

    @InjectMocks
    private VerificationController verificationController;

    @Test
    void verifyValidEmail_ShouldReturnOk_WhenEmailIsValid() {
        String email = "valid@example.com";
        when(verificationService.isValidEmail(email)).thenReturn(true);

        ResponseEntity<String> response = verificationController.verifyValidEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email is valid.", response.getBody());
    }

    @Test
    void verifyValidEmail_ShouldReturnBadRequest_WhenEmailIsInvalid() {
        String email = "invalid-email";
        when(verificationService.isValidEmail(email)).thenReturn(false);

        ResponseEntity<String> response = verificationController.verifyValidEmail(email);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email format: " + email, response.getBody());
    }

    @Test
    void verifyAvailableEmail_ShouldReturnOk_WhenEmailIsAvailable() {
        String email = "new@example.com";
        when(verificationService.isAvailableEmail(email)).thenReturn(true);

        ResponseEntity<String> response = verificationController.verifyAvailableEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email is available.", response.getBody());
    }

    @Test
    void verifyAvailableEmail_ShouldReturnConflict_WhenEmailIsTaken() {
        String email = "taken@example.com";
        when(verificationService.isAvailableEmail(email)).thenReturn(false);

        ResponseEntity<String> response = verificationController.verifyAvailableEmail(email);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email " + email + " is already taken", response.getBody());
    }
}
