package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.events.MessageSentEvent;
import com.example.volunteer_platform.server.mapper.MessageMapper;
import com.example.volunteer_platform.server.model.Message;
import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.repository.NotificationRepository;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    private final NotificationRepository notificationRepository;
    private final ApplicationEventPublisher publisher;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageService(NotificationRepository notificationRepository, ApplicationEventPublisher publisher,
                          MessageMapper messageMapper) {
        this.notificationRepository = notificationRepository;
        this.publisher = publisher;
        this.messageMapper = messageMapper;
    }

    public MessageResponseDTO sendMessage(String messageText, User sender, User recipient) {
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setMessage(messageText);
        message.setType(Notification.NotificationType.MESSAGE);
        message.setCreatedAt(LocalDateTime.now());
        message.setRead(false);

        Message savedMessage = notificationRepository.save(message);

        publisher.publishEvent(new MessageSentEvent(this, messageText, sender, recipient));

        return messageMapper.toMessageResponseDTO(savedMessage);
    }

}
