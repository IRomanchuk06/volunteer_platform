package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.events.MessageSentEvent;
import com.example.volunteer_platform.server.mapper.NotificationMapper;
import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.repository.NotificationRepository;
import com.example.volunteer_platform.shared_dto.NotificationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public MessageService(NotificationRepository notificationRepository, NotificationMapper notificationMapper,
                          ApplicationEventPublisher publisher) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.publisher = publisher;
    }

    public NotificationResponseDTO sendMessage(String message, User sender, User recipient) {
        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setType(Notification.NotificationType.MESSAGE);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        Notification savedNotification = notificationRepository.save(notification);

        publisher.publishEvent(new MessageSentEvent(this, message, sender, recipient));

        return notificationMapper.toNotificationResponseDTO(savedNotification);
    }

    public void responseToEvent(Long eventId, Long userId) {
    }
}
