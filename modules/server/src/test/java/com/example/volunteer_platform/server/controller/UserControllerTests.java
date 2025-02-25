package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.config.JacksonConfig;
import com.example.volunteer_platform.server.controller.advice.GlobalExceptionHandler;
import com.example.volunteer_platform.server.exceptions.UserNotFoundException;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.UserService;
import com.example.volunteer_platform.shared_dto.MessageRegistrationDTO;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.UpdateUserDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Objects;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final JacksonConfig jacksonConfig = new JacksonConfig();

    private final Customer testUser = Customer.builder().id(1L).email("test@example.com").username("testUser").password(
            "password123").role(User.UserRole.CUSTOMER).build();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getUserByEmail_UserExists_ReturnsUser() throws Exception {
        when(userService.getUserByEmail("test@example.com")).thenReturn(testUser);

        mockMvc.perform(get("/users/email/test@example.com")).andExpect(status().isOk()).andExpect(
                jsonPath("$.email").value("test@example.com")).andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void getUserByEmail_UserNotFound_ThrowsException() throws Exception {
        when(userService.getUserByEmail(anyString())).thenThrow(
                new UserNotFoundException("User not found with email: unknown@example.com"));

        mockMvc.perform(get("/users/email/unknown@example.com")).andExpect(status().isNotFound()).andExpect(
                content().string("User not found with email: unknown@example.com"));
    }

    @Test
    void getUserByUsername_UserExists_ReturnsUser() throws Exception {
        when(userService.getUserByUsername("testUser")).thenReturn(testUser);

        mockMvc.perform(get("/users/username/testUser")).andExpect(status().isOk()).andExpect(
                jsonPath("$.username").value("testUser")).andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getUserByUsername_UserNotFound_ThrowsException() throws Exception {
        when(userService.getUserByUsername("unknownUser")).thenThrow(
                new UserNotFoundException("User not found with username: unknownUser"));

        mockMvc.perform(get("/users/username/unknownUser")).andExpect(status().isNotFound()).andExpect(
                content().string("User not found with username: unknownUser"));
    }

    @Test
    void updateUser_ValidRequest_ReturnsUpdatedUser() throws Exception {
        UpdateUserDTO updateRequest = new UpdateUserDTO("test@example.com", "newUsername", "newPassword",
                "oldPassword");

        UserResponseDTO updatedUser = new UserResponseDTO(1L, "newUsername", "newPassword", "test@example.com",
                User.UserRole.CUSTOMER.toString());

        when(userService.updateUser("test@example.com", "newUsername", "oldPassword", "newPassword")).thenReturn(
                updatedUser);

        mockMvc.perform(put("/users/update").contentType(MediaType.APPLICATION_JSON).content(
                jacksonConfig.objectMapper().writeValueAsString(updateRequest))).andExpect(status().isOk()).andExpect(
                jsonPath("$.username").value("newUsername")).andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void deleteUser_UserExists_ReturnsNoContent() throws Exception {
        when(userService.deleteUser("test@example.com")).thenReturn(true);

        mockMvc.perform(delete("/users/delete").param("email", "test@example.com")).andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_UserNotFound_ThrowsException() throws Exception {
        mockMvc.perform(delete("/users/delete").param("email", "unknown@example.com"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with email: unknown@example.com")); // Ожидаем сообщение
    }

    @Test
    void sendMessage_ValidRequest_ReturnsMessageResponse() throws Exception {
        MessageRegistrationDTO messageRequest = new MessageRegistrationDTO("Hello!", "recipient@example.com");
        MessageResponseDTO responseDTO = new MessageResponseDTO(
                100L, "test@example.com", "recipient@example.com", "Hello!", "TEXT", null, false);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("currentUser", testUser);
        request.setSession(session);

        when(userService.sendMessage("Hello!", "recipient@example.com", "test@example.com")).thenReturn(responseDTO);

        mockMvc.perform(post("/users/messages/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonConfig.objectMapper().writeValueAsString(messageRequest))
                        .session((MockHttpSession) Objects.requireNonNull(request.getSession())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello!"))
                .andExpect(jsonPath("$.senderEmail").value("test@example.com"))
                .andExpect(jsonPath("$.recipientEmail").value("recipient@example.com"));
    }
}
