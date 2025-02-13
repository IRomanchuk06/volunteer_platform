package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.CustomerService;
import com.example.volunteer_platform.shared_dto.EventRegistrationDTO;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.UserRegistrationDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerControllerTests {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private User currentUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer_Success() {
        UserRegistrationDTO accountRequest = new UserRegistrationDTO();
        accountRequest.setEmail("test@example.com");
        accountRequest.setPassword("password");
        accountRequest.setUsername("testuser");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail("test@example.com");
        userResponseDTO.setUsername("testuser");

        when(customerService.createCustomer(any(), any(), any())).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> responseEntity = customerController.createCustomer(accountRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(customerService, times(1)).createCustomer(any(), any(), any());
    }

    @Test
    void testCreateEvent_Success() {
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

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(currentUser);

        when(customerService.createEvent(any(), any(), any(), any(), any(), any(), any(), anyInt()))
                .thenReturn(eventResponseDTO);

        ResponseEntity<EventResponseDTO> responseEntity = customerController.createEvent(eventRequest, request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(customerService, times(1)).createEvent(any(), any(), any(), any(), any(), any(), any(), anyInt());
    }
}
