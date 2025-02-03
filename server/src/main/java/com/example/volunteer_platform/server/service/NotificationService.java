package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.mapper.MessageMapper;
import com.example.volunteer_platform.server.mapper.VolunteerResponseMapper;
import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.repository.NotificationRepository;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerEventResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final VolunteerResponseMapper volunteerResponseMapper;
    private final MessageMapper messageMapper;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, VolunteerResponseMapper volunteerResponseMapper,
                               MessageMapper messageMapper) {
        this.notificationRepository = notificationRepository;
        this.volunteerResponseMapper = volunteerResponseMapper;
        this.messageMapper = messageMapper;
    }

    public List<VolunteerEventResponseDTO> getVolunteerResponses(User recipient) {
        List<Notification> volunteerResponses = notificationRepository.findByRecipientAndType(recipient,
                Notification.NotificationType.VOLUNTEER_RESPONSE);
        return volunteerResponseMapper.toVolunteerResponseDTOList(volunteerResponses);
    }

    public List<MessageResponseDTO> getReceivedMessages(User recipient) {
        List<Notification> notifications = notificationRepository.findByRecipientAndType(recipient,
                Notification.NotificationType.MESSAGE);
        return messageMapper.toMessageResponseDTOList(notifications);
    }
}
