package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.Response;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ResponseService {

    private final NotificationRepository notificationRepository;

    public ResponseService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void responseToEvent(Event event, Volunteer volunteer) {
        Response response = new Response();
        response.setEvent(event);
        response.setRead(false);
        response.setSender(volunteer);
        response.setRecipient(event.getCustomer());
        response.setCreatedAt(LocalDateTime.now());
        response.setType(Notification.NotificationType.VOLUNTEER_RESPONSE);

        notificationRepository.save(response);
    }
}
