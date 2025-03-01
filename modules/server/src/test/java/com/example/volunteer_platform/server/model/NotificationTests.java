package com.example.volunteer_platform.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

    private Message message;

    @BeforeEach
    void setUp() {
        message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setType(Notification.NotificationType.MESSAGE);
        message.setCreatedAt(LocalDateTime.now());
        message.setRead(false);
    }

    @Nested
    class BasicFieldsTests {
        @Test
        void shouldHandleAllFieldsCorrectly() {
            assertAll(
                    () -> assertSame(sender, message.getSender()),
                    () -> assertSame(recipient, message.getRecipient()),
                    () -> assertEquals(Notification.NotificationType.MESSAGE, message.getType()),
                    () -> assertNotNull(message.getCreatedAt()),
                    () -> assertFalse(message.isRead())
            );
        }
    }

    @Nested
    class StateChangeTests {
        @Test
        void shouldUpdateReadStatus() {
            message.setRead(true);
            assertTrue(message.isRead());
        }

        @Test
        void shouldHandleNotificationTypeChanges() {
            message.setType(Notification.NotificationType.SYSTEM_ALERT);
            assertEquals(Notification.NotificationType.SYSTEM_ALERT, message.getType());
        }
    }

    @Nested
    class JsonHandlingTests {
        @Test
        void shouldMaintainJsonReferences() {
            assertAll(
                    () -> assertNotNull(message.getSender()),
                    () -> assertNotNull(message.getRecipient()),
                    () -> verifyNoInteractions(sender, recipient)
            );
        }
    }

    @Nested
    class NullHandlingTests {
        @Test
        void shouldHandleNullValuesSafely() {
            message.setSender(null);
            message.setRecipient(null);
            message.setType(null);

            assertAll(
                    () -> assertNull(message.getSender()),
                    () -> assertNull(message.getRecipient()),
                    () -> assertNull(message.getType())
            );
        }
    }

    @Nested
    class ConstructorTests {
        @Test
        void shouldCreateNotificationWithAllArgsConstructor() {
            Notification notification = new Notification(
                    1L,
                    sender,
                    recipient,
                    Notification.NotificationType.MESSAGE,
                    LocalDateTime.now(),
                    false
            ) {};

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
}