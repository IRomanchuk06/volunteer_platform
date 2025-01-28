package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.events.VolunteerRespondedEvent;
import com.example.volunteer_platform.server.mapper.NotificationMapper;
import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.repository.NotificationRepository;
import com.example.volunteer_platform.shared_dto.NotificationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    public List<NotificationResponseDTO> getSentNotifications(User sender) {
        List<Notification> notifications = notificationRepository.findBySender(sender);
        return notificationMapper.toNotificationResponseDTOList(notifications);
    }

    public List<NotificationResponseDTO> getReceivedNotifications(User recipient) {
        List<Notification> notifications = notificationRepository.findBySender(recipient);
        return notificationMapper.toNotificationResponseDTOList(notifications);
    }

    public List<NotificationResponseDTO> getVolunteerResponses(User recipient) {
        List<Notification> notifications = notificationRepository.findByRecipientAndType(recipient,
                Notification.NotificationType.VOLUNTEER_RESPONSE);
        return notificationMapper.toNotificationResponseDTOList(notifications);
    }
}
