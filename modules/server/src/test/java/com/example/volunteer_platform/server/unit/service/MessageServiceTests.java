package com.example.volunteer_platform.server.unit.service;

import com.example.volunteer_platform.server.events.MessageSentEvent;
import com.example.volunteer_platform.server.mapper.MessageMapper;
import com.example.volunteer_platform.server.model.Message;
import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.repository.NotificationRepository;
import com.example.volunteer_platform.server.service.MessageService;
import com.example.volunteer_platform.server.unit.BaseUnitTests;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTests extends BaseUnitTests {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private MessageService messageService;

    @Test
    void testSendMessage_Success() {
        String messageText = "Hello, this is a test message!";
        User sender = new Volunteer();
        sender.setEmail("sender@example.com");
        sender.setPassword("password123");

        User recipient = new Volunteer();
        recipient.setEmail("recipient@example.com");
        recipient.setPassword("password123");

        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setMessage(messageText);
        message.setType(Notification.NotificationType.MESSAGE);
        message.setCreatedAt(LocalDateTime.now());
        message.setRead(false);

        when(notificationRepository.save(any(Message.class))).thenReturn(message);
        MessageResponseDTO responseDTO = new MessageResponseDTO();
        when(messageMapper.toMessageResponseDTO(message)).thenReturn(responseDTO);

        MessageResponseDTO result = messageService.sendMessage(messageText, sender, recipient);

        assertNotNull(result);
        verify(notificationRepository, times(1)).save(any(Message.class));
        verify(publisher, times(1)).publishEvent(any(MessageSentEvent.class));
        verify(messageMapper, times(1)).toMessageResponseDTO(message);
    }

    @Test
    void testSendMessage_NotifyEvent() {
        String messageText = "Hello, this is a test message!";
        User sender = new Volunteer();
        sender.setEmail("sender@example.com");
        sender.setPassword("password123");

        User recipient = new Volunteer();
        recipient.setEmail("recipient@example.com");
        recipient.setPassword("password123");

        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setMessage(messageText);
        message.setType(Notification.NotificationType.MESSAGE);
        message.setCreatedAt(LocalDateTime.now());
        message.setRead(false);

        when(notificationRepository.save(any(Message.class))).thenReturn(message);
        MessageResponseDTO responseDTO = new MessageResponseDTO();
        when(messageMapper.toMessageResponseDTO(message)).thenReturn(responseDTO);

        messageService.sendMessage(messageText, sender, recipient);

        verify(publisher, times(1)).publishEvent(any(MessageSentEvent.class));
    }
}
