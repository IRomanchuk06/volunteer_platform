package com.example.volunteer_platform.server.unit.controller;

import com.example.volunteer_platform.server.controller.AuthenticationController;
import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.AuthenticationService;
import com.example.volunteer_platform.server.unit.BaseUnitTests;
import com.example.volunteer_platform.shared_dto.UserLoginDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTests extends BaseUnitTests {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthenticationController authenticationController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void testLogin_Success() throws Exception {
        UserLoginDTO loginRequest = new UserLoginDTO();
        loginRequest.setEmail("customer@example.com");
        loginRequest.setPassword("password123");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("customer@example.com");
        customer.setPassword("password123");
        customer.setRole(User.UserRole.CUSTOMER);
        customer.setUsername("customer");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setUsername("customer");
        userResponseDTO.setEmail("customer@example.com");
        userResponseDTO.setPassword("password123");
        userResponseDTO.setRole("CUSTOMER");

        when(authenticationService.authenticate(any(), any())).thenReturn(customer);
        when(userMapper.toUserResponseDTO((User) customer)).thenReturn(userResponseDTO);

        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(loginRequest))).andExpect(status().isOk()).andExpect(
                jsonPath("$.email").value("customer@example.com")).andExpect(
                jsonPath("$.username").value("customer")).andExpect(jsonPath("$.role").value("CUSTOMER")).andExpect(
                cookie().exists("JSESSIONID")).andExpect(request().sessionAttribute("currentUser", customer));

        verify(authenticationService, times(1)).authenticate(any(), any());
        verify(userMapper, times(1)).toUserResponseDTO((User) customer);
    }


    @Test
    void testGetUserProfile_Success() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("customer@example.com");
        customer.setUsername("customer");
        customer.setPassword("password123");
        customer.setRole(User.UserRole.CUSTOMER);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setUsername("customer");
        userResponseDTO.setEmail("customer@example.com");
        userResponseDTO.setPassword("password123");
        userResponseDTO.setRole("CUSTOMER");

        when(userMapper.toUserResponseDTO((User) customer)).thenReturn(userResponseDTO);

        mockMvc.perform(get("/auth/profile").sessionAttr("currentUser", customer)).andExpect(status().isOk()).andExpect(
                jsonPath("$.id").value(1L)).andExpect(jsonPath("$.username").value("customer")).andExpect(
                jsonPath("$.email").value("customer@example.com")).andExpect(jsonPath("$.role").value("CUSTOMER"));

        verify(userMapper, times(1)).toUserResponseDTO((User) customer);
    }


    @Test
    void testLogout_Success() throws Exception {
        Customer customer = new Customer();
        mockMvc.perform(post("/auth/logout").sessionAttr("CurrentUser", customer)).andExpect(status().isOk()).andExpect(
                content().string("true"));
    }

    @Test
    void testLogout_Failure_NoActiveSession() throws Exception {
        mockMvc.perform(post("/auth/logout")).andExpect(status().isUnauthorized()).andExpect(content().string("false"));
    }
}
