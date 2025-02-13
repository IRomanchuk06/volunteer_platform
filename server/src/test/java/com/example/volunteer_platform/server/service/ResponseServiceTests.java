package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.model.*;
import com.example.volunteer_platform.server.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ResponseServiceTests {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private ResponseService responseService;

    @Test
    void testResponseToEvent_Success() {
        Event event = mock(Event.class);
        Volunteer volunteer = new Volunteer();
        volunteer.setEmail("volunteer@example.com");
        volunteer.setPassword("password123");

        Customer customer = mock(Customer.class);
        when(event.getCustomer()).thenReturn(customer);

        Response response = new Response();
        response.setEvent(event);
        response.setSender(volunteer);
        response.setRecipient(customer);
        response.setCreatedAt(LocalDateTime.now());
        response.setType(Notification.NotificationType.VOLUNTEER_RESPONSE);
        response.setRead(false);

        when(notificationRepository.save(any(Response.class))).thenReturn(response);

        responseService.responseToEvent(event, volunteer);

        verify(notificationRepository, times(1)).save(any(Response.class));
    }

    @Test
    void testResponseToEvent_CorrectProperties() {
        Event event = mock(Event.class);
        Volunteer volunteer = new Volunteer();
        volunteer.setEmail("volunteer@example.com");
        volunteer.setPassword("password123");
        Customer customer = mock(Customer.class);
        when(event.getCustomer()).thenReturn(customer);

        responseService.responseToEvent(event, volunteer);

        verify(notificationRepository).save(argThat(response ->
                response.getSender().equals(volunteer) &&
                        response.getRecipient().equals(customer) &&
                        response.getType().equals(Notification.NotificationType.VOLUNTEER_RESPONSE) &&
                        !response.isRead()
        ));
    }
}
