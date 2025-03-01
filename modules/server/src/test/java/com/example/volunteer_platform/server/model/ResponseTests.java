package com.example.volunteer_platform.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ResponseTests {

    @Mock
    private User sender;

    @Mock
    private User recipient;

    @Mock
    private Event event1;

    @Mock
    private Event event2;

    private Response response;

    @BeforeEach
    void setUp() {
        response = new Response();
        response.setId(1L);
        response.setSender(sender);
        response.setRecipient(recipient);
        response.setType(Notification.NotificationType.VOLUNTEER_RESPONSE);
        response.setCreatedAt(LocalDateTime.now());
        response.setRead(false);
        response.setEvent(event1);
    }

    @Nested
    class InheritanceTests {
        @Test
        void shouldInheritNotificationFields() {
            assertAll(
                    () -> assertEquals(1L, response.getId()),
                    () -> assertSame(sender, response.getSender()),
                    () -> assertEquals(Notification.NotificationType.VOLUNTEER_RESPONSE, response.getType())
            );
        }
    }

    @Nested
    class EqualsAndHashCodeTests {
        @Test
        void shouldBeEqualWithSameIdAndIgnoredEvent() {
            Response anotherResponse = new Response();
            anotherResponse.setId(1L);
            anotherResponse.setEvent(event2);

            assertEquals(response, anotherResponse);
            assertEquals(response.hashCode(), anotherResponse.hashCode());
        }

        @Test
        void shouldNotBeEqualWithDifferentId() {
            Response anotherResponse = new Response();
            anotherResponse.setId(2L);

            assertNotEquals(response, anotherResponse);
        }
    }

    @Nested
    class ToStringTests {
        @Test
        void shouldExcludeEventField() {
            String toStringResult = response.toString();
            assertFalse(toStringResult.contains("event="));
        }
    }

    @Nested
    class RelationshipTests {
        @Test
        void shouldMaintainEventRelationship() {
            response.setEvent(event2);
            assertSame(event2, response.getEvent());
        }

        @Test
        void shouldHandleNullEvent() {
            response.setEvent(null);
            assertNull(response.getEvent());
        }
    }

    @Test
    void shouldImplementCanEqualProperly() {
        Notification notification = new Response();
        assertTrue(response.canEqual(notification));
        assertFalse(response.canEqual(new Object()));
    }
}