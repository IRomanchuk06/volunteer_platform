package com.example.volunteer_platform.server.unit.controller;

import com.example.volunteer_platform.server.controller.VerificationController;
import com.example.volunteer_platform.server.controller.advice.GlobalExceptionHandler;
import com.example.volunteer_platform.server.service.VerificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VerificationControllerTests {

    @Mock
    private VerificationService verificationService;

    @InjectMocks
    private VerificationController verificationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(verificationController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void verifyValidEmail_ShouldReturnOk_WhenEmailIsValid() throws Exception {
        String email = "valid@example.com";
        when(verificationService.isValidEmail(email)).thenReturn(true);

        mockMvc.perform(get("/verification/emails/valid")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("Email is valid."));
    }

    @Test
    void verifyValidEmail_ShouldReturnBadRequest_WhenEmailIsInvalid() throws Exception {
        String email = "invalid-email";
        when(verificationService.isValidEmail(email)).thenReturn(false);

        mockMvc.perform(get("/verification/emails/valid")
                        .param("email", email))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid email format: " + email));
    }

    @Test
    void verifyAvailableEmail_ShouldReturnOk_WhenEmailIsAvailable() throws Exception {
        String email = "new@example.com";
        when(verificationService.isAvailableEmail(email)).thenReturn(true);

        mockMvc.perform(get("/verification/emails/available")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("Email is available."));
    }

    @Test
    void verifyAvailableEmail_ShouldReturnConflict_WhenEmailIsTaken() throws Exception {
        String email = "taken@example.com";
        when(verificationService.isAvailableEmail(email)).thenReturn(false);

        mockMvc.perform(get("/verification/emails/available")
                        .param("email", email))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email " + email + " is already taken"));
    }
}
