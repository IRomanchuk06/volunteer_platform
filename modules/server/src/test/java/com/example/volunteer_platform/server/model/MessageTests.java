package com.example.volunteer_platform.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MessageTests {

    @Mock
    private User sender;

    @Mock
    private User recipient;

    @Test
    void shouldInheritFromNotificationAndSetMessage() {
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setMessage("Test message");
        message.setType(Notification.NotificationType.MESSAGE);
        message.setCreatedAt(LocalDateTime.now());
        message.setRead(false);
        message.setId(1L);

        assertAll(
                () -> assertEquals(1L, message.getId()),
                () -> assertSame(sender, message.getSender()),
                () -> assertSame(recipient, message.getRecipient()),
                () -> assertEquals(Notification.NotificationType.MESSAGE, message.getType()),
                () -> assertNotNull(message.getCreatedAt()),
                () -> assertFalse(message.isRead())
        );

        assertEquals("Test message", message.getMessage());
    }
}