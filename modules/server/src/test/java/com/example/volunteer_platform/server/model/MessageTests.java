package com.example.volunteer_platform.server.model;

import org.junit.jupiter.api.Nested;
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

    private Message createTestMessage(Long id, String text) {
        Message message = new Message();
        message.setId(id);
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setMessage(text);
        message.setType(Notification.NotificationType.MESSAGE);
        message.setCreatedAt(LocalDateTime.now());
        message.setRead(false);
        return message;
    }

    @Nested
    class InheritanceTests {
        @Test
        void shouldInheritFromNotificationAndSetMessage() {
            Message message = createTestMessage(1L, "Test message");

            assertAll(
                    () -> assertEquals(1L, message.getId()),
                    () -> assertEquals("Test message", message.getMessage()),
                    () -> assertSame(sender, message.getSender()),
                    () -> assertEquals(Notification.NotificationType.MESSAGE, message.getType())
            );
        }
    }

    @Nested
    class EqualsAndHashCodeTests {
        @Test
        void shouldBeEqualWithSameIdAndMessage() {
            Message msg1 = createTestMessage(1L, "Hello");
            Message msg2 = createTestMessage(1L, "Hello");

            assertEquals(msg1, msg2);
            assertEquals(msg1.hashCode(), msg2.hashCode());
        }

        @Test
        void shouldNotBeEqualWithDifferentMessage() {
            Message msg1 = createTestMessage(1L, "Hello");
            Message msg2 = createTestMessage(1L, "Hi");

            assertNotEquals(msg1, msg2);
        }

        @Test
        void shouldNotBeEqualWithDifferentId() {
            Message msg1 = createTestMessage(1L, "Hello");
            Message msg2 = createTestMessage(2L, "Hello");

            assertNotEquals(msg1, msg2);
        }

        @Test
        void shouldImplementCanEqualCorrectly() {
            Message msg = createTestMessage(1L, "Test");
            Notification notification = new Message();
            notification.setId(1L);

            assertTrue(msg.canEqual(notification));
            assertFalse(msg.canEqual(new Object()));
        }
    }

    @Nested
    class ToStringTests {
        @Test
        void shouldIncludeMessageInToString() {
            Message message = createTestMessage(1L, "Important!");
            String toString = message.toString();

            assertAll(
                    () -> assertTrue(toString.contains("message=Important!")),
                    () -> assertTrue(toString.contains("id=1"))
            );
        }
    }

    @Nested
    class NullHandlingTests {
        @Test
        void shouldHandleNullMessage() {
            Message message = new Message();
            message.setMessage(null);

            assertNull(message.getMessage());
            assertDoesNotThrow(message::hashCode);
        }
    }
}