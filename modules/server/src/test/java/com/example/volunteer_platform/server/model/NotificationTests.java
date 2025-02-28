package com.example.volunteer_platform.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationTests {

    @Mock
    private User sender;

    @Mock
    private User recipient;

    private Message notification;

    @BeforeEach
    void setUp() {
        notification = new Message();
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notification.setType(Notification.NotificationType.MESSAGE);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
    }

    @Test
    void shouldHandleAllFieldsCorrectly() {
        assertAll(
                () -> assertSame(sender, notification.getSender()),
                () -> assertSame(recipient, notification.getRecipient()),
                () -> assertEquals(Notification.NotificationType.MESSAGE, notification.getType()),
                () -> assertNotNull(notification.getCreatedAt()),
                () -> assertFalse(notification.isRead())
        );
    }

    @Test
    void shouldUpdateReadStatus() {
        notification.setRead(true);
        assertTrue(notification.isRead());
    }

    @Test
    void shouldHandleNotificationTypeChanges() {
        notification.setType(Notification.NotificationType.SYSTEM_ALERT);
        assertEquals(Notification.NotificationType.SYSTEM_ALERT, notification.getType());
    }

    @Test
    void shouldMaintainJsonReferences() {
        assertAll(
                () -> assertNotNull(notification.getSender()),
                () -> assertNotNull(notification.getRecipient()),
                () -> verifyNoInteractions(sender, recipient) // Моки не должны вызывать реальных методов
        );
    }

    @Test
    void shouldHandleNullValuesSafely() {
        notification.setSender(null);
        notification.setRecipient(null);
        notification.setType(null);

        assertAll(
                () -> assertNull(notification.getSender()),
                () -> assertNull(notification.getRecipient()),
                () -> assertNull(notification.getType())
        );
    }

    @Test
    void shouldCreateNotificationWithAllArgsConstructor() {
        Notification notification = new Notification(
                1L,
                sender,
                recipient,
                Notification.NotificationType.MESSAGE,
                LocalDateTime.now(),
                false
        ) {
        };

        assertAll(
                () -> assertEquals(1L, notification.getId()),
                () -> assertSame(sender, notification.getSender()),
                () -> assertSame(recipient, notification.getRecipient()),
                () -> assertEquals(Notification.NotificationType.MESSAGE, notification.getType()),
                () -> assertNotNull(notification.getCreatedAt()),
                () -> assertFalse(notification.isRead())
        );
    }
}