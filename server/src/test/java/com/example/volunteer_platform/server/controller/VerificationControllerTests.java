package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.controller.advice.GlobalExceptionHandler;
import com.example.volunteer_platform.server.exeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.server.exeptions.InvalidEmailException;
import com.example.volunteer_platform.server.service.VerificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VerificationController.class)
@Import(GlobalExceptionHandler.class)
class VerificationControllerTests {

    @Mock
    private VerificationService verificationService;

    @InjectMocks
    private VerificationController verificationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(verificationController).build();
    }

    @Test
    void verifyValidEmail_ShouldReturnOk_WhenEmailIsValid() throws Exception {
        String email = "valid@example.com";
        when(verificationService.isValidEmail(email)).thenReturn(true);

        mockMvc.perform(get("/verification/emails/valid")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Email is valid."));
    }

    @Test
    void verifyValidEmail_ShouldReturnBadRequest_WhenEmailIsInvalid() throws Exception {
        String email = "invalid-email";

        // 1. Правильная настройка мока
        when(verificationService.isValidEmail(email)).thenReturn(false);

        // 2. Используем правильный полный путь
        mockMvc.perform(get("/verification/emails/valid")  // Совпадает с логом запроса
                        .param("email", email))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid email format: " + email))
                .andDo(print());  // Для отладки
    }

    @Test
    void verifyAvailableEmail_ShouldReturnOk_WhenEmailIsAvailable() throws Exception {
        String email = "new@example.com";
        when(verificationService.isAvailableEmail(email)).thenReturn(true);

        mockMvc.perform(get("/verification/emails/available")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Email is available."));
    }

    @Test
    void verifyAvailableEmail_ShouldReturnConflict_WhenEmailIsTaken() throws Exception {
        String email = "taken@example.com";
        when(verificationService.isAvailableEmail(email)).thenReturn(false);

        mockMvc.perform(get("/verification/emails/available")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email " + email + " is already taken"));
    }
}
