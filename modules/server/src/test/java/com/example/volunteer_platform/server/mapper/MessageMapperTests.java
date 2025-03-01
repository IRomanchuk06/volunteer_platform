package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.server.model.*;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageMapperTests {

    private final MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);

    @Test
    void shouldMapMessageToDTOWithAllFields() {
        Customer sender = Customer.builder()
                .email("sender@test.com")
                .build();

        Volunteer recipient = Volunteer.builder()
                .email("recipient@test.com")
                .build();

        Message message = Message.builder()
                .id(1L)
                .type(Notification.NotificationType.MESSAGE)
                .sender(sender)
                .recipient(recipient)
                .message("Test content")
                .createdAt(LocalDateTime.now())
                .isRead(true)
                .build();

        MessageResponseDTO dto = messageMapper.toMessageResponseDTO(message);

        assertAll(
                () -> assertEquals(1L, dto.getId()),
                () -> assertEquals("MESSAGE", dto.getType()),
                () -> assertEquals("sender@test.com", dto.getSenderEmail()),
                () -> assertEquals("recipient@test.com", dto.getRecipientEmail()),
                () -> assertEquals("Test content", dto.getMessage()),
                () -> assertEquals(message.getCreatedAt(), dto.getCreatedAt()),
                () -> assertTrue(dto.isRead())
        );
    }

    @Test
    void shouldConvertNotificationListToMessageResponseDTOList() {
        Customer sender = Customer.builder()
                .email("sender@test.com")
                .build();

        Volunteer recipient = Volunteer.builder()
                .email("recipient@test.com")
                .build();

        Message message1 = Message.builder()
                .id(1L)
                .type(Notification.NotificationType.MESSAGE)
                .sender(sender)
                .recipient(recipient)
                .message("Message 1")
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();

        Message message2 = Message.builder()
                .id(2L)
                .type(Notification.NotificationType.MESSAGE)
                .sender(sender)
                .recipient(recipient)
                .message("Message 2")
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();

        Notification otherNotification = new Notification() {};

        List<Notification> notifications = List.of(message1, message2, otherNotification);

        List<MessageResponseDTO> result = messageMapper.toMessageResponseDTOList(notifications);

        assertEquals(2, result.size());
        assertEquals("Message 1", result.get(0).getMessage());
        assertEquals("Message 2", result.get(1).getMessage());
    }

    @Test
    void shouldReturnEmptyListForNullInput() {
        List<MessageResponseDTO> result = messageMapper.toMessageResponseDTOList(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListForEmptyInput() {
        List<MessageResponseDTO> result = messageMapper.toMessageResponseDTOList(Collections.emptyList());
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFilterOutNonMessageNotifications() {
        Notification otherNotification = new Notification() {}; // Другой тип уведомления
        List<Notification> notifications = List.of(otherNotification);

        List<MessageResponseDTO> result = messageMapper.toMessageResponseDTOList(notifications);
        assertTrue(result.isEmpty());
    }
}