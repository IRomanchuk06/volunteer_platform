package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.mapper.NotificationMapper;
import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.repository.NotificationRepository;
import com.example.volunteer_platform.shared_dto.NotificationResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Notification> notifications = notificationRepository.findByRecipient(recipient);
        return notificationMapper.toNotificationResponseDTOList(notifications);
    }

    public List<VolunteerResponseDTO> getVolunteerResponses(User recipient) {
        List<Notification> volunteerResponses = notificationRepository.findByRecipientAndType(recipient,
                Notification.NotificationType.VOLUNTEER_RESPONSE);
        return notificationMapper.toVolunteerResponseDTOList(volunteerResponses);
    }

    public List<NotificationResponseDTO> getReceivedMessages(User recipient) {
        List<Notification> notifications = notificationRepository.findByRecipientAndType(recipient,
                Notification.NotificationType.MESSAGE);
        return notificationMapper.toNotificationResponseDTOList(notifications);
    }
}
