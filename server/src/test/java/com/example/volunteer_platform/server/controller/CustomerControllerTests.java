package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.config.JacksonConfig;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.service.CustomerService;
import com.example.volunteer_platform.shared_dto.EventRegistrationDTO;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.UserRegistrationDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTests {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @Mock
    private Customer currentUser;

    @InjectMocks
    private CustomerController customerController;

    private final JacksonConfig jacksonConfig = new JacksonConfig();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .build();
    }

    @Test
    void testCreateCustomer_Success() throws Exception {
        UserRegistrationDTO accountRequest = new UserRegistrationDTO();
        accountRequest.setEmail("test@example.com");
        accountRequest.setPassword("password");
        accountRequest.setUsername("testUser");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setUsername("testUser");
        userResponseDTO.setPassword("password");
        userResponseDTO.setEmail("test@example.com");
        userResponseDTO.setRole("CUSTOMER");

        when(customerService.createCustomer(any(), any(), any())).thenReturn(userResponseDTO);

        mockMvc.perform(post("/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonConfig.objectMapper().writeValueAsString(accountRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.role").value("CUSTOMER"))
                .andExpect(jsonPath("$.id").value(1L));

        verify(customerService, times(1)).createCustomer(any(), any(), any());
    }

    @Test
    void testCreateEvent_Success() throws Exception {
        EventRegistrationDTO eventRequest = new EventRegistrationDTO();
        eventRequest.setName("Test Event");
        eventRequest.setDescription("Test Description");
        eventRequest.setLocation("Test Location");
        eventRequest.setDate(LocalDate.of(2025, 2, 13));
        eventRequest.setStartTime(LocalTime.of(8, 0));
        eventRequest.setEndTime(LocalTime.of(12, 0));
        eventRequest.setNumOfRequiredVolunteers(5);

        EventResponseDTO eventResponseDTO = new EventResponseDTO();
        eventResponseDTO.setName("Test Event");
        eventResponseDTO.setDescription("Test Description");

        when(customerService.createEvent(any(), any(), any(), any(), any(), any(), any(), anyInt()))
                .thenReturn(eventResponseDTO);

        mockMvc.perform(post("/customers/events/")
                        .sessionAttr("currentUser", currentUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonConfig.objectMapper().writeValueAsString(eventRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Event"))
                .andExpect(jsonPath("$.description").value("Test Description"));

        verify(customerService, times(1)).createEvent(any(), any(), any(), any(), any(), any(), any(), anyInt());
    }
}
