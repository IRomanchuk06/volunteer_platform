package com.example.volunteer_platform.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserTests {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User() {};
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setEmail("test@example.com");
        user.setRole(User.UserRole.CUSTOMER);
    }

    @Nested
    class InitializationTests {
        @Test
        void testAllArgsConstructor() {
            List<Notification> notifications = new ArrayList<>();
            User user = new User(1L, "testUser", "testPassword", "test@example.com", User.UserRole.CUSTOMER, notifications) {};

            assertAll(
                    () -> assertEquals(1L, user.getId()),
                    () -> assertEquals("testUser", user.getUsername()),
                    () -> assertEquals("test@example.com", user.getEmail())
            );
        }

        @Test
        void testAllArgsConstructorWithNullValues() {
            List<Notification> notifications = new ArrayList<>();
            User user = new User(null, null, null, null, null, notifications) {};

            assertAll(
                    () -> assertNull(user.getId()),
                    () -> assertNull(user.getUsername()),
                    () -> assertNull(user.getRole())
            );
        }
    }

    @Nested
    class GetterSetterTests {
        @Test
        void testGettersAndSetters() {
            assertAll(
                    () -> assertEquals(1L, user.getId()),
                    () -> assertEquals("testPassword", user.getPassword()),
                    () -> assertEquals(User.UserRole.CUSTOMER, user.getRole())
            );
        }

        @Test
        void testSetNotifications() {
            List<Notification> newNotifications = new ArrayList<>();
            newNotifications.add(new Message());

            user.setNotifications(newNotifications);
            assertEquals(1, user.getNotifications().size());
        }

        @Test
        void testSetNullNotifications() {
            user.setNotifications(null);
            assertNull(user.getNotifications());
        }
    }

    @Nested
    class NotificationsManagementTests {
        @Test
        void testAddNullNotification() {
            user.getNotifications().add(null);
            assertEquals(1, user.getNotifications().size());
        }

        @Test
        void testRemoveNotification() {
            Notification notification = new Message();
            user.getNotifications().add(notification);
            user.getNotifications().remove(notification);

            assertTrue(user.getNotifications().isEmpty());
        }
    }

    @Nested
    class EqualsAndHashCodeTests {
        @Test
        void testEqualsAndHashCode() {
            User user1 = createUser(1L, "user");
            User user2 = createUser(1L, "user");

            assertEquals(user1, user2);
            assertEquals(user1.hashCode(), user2.hashCode());
        }

        @Test
        void testNotEquals() {
            User user1 = createUser(1L, "user1");
            User user2 = createUser(2L, "user2");

            assertNotEquals(user1, user2);
        }

        @Test
        void testEqualsWithDifferentFields() {
            User user1 = createUser(1L, "user1");
            User user2 = createUser(1L, "user2");

            assertNotEquals(user1, user2);
        }

        @Test
        void testHashCodeConsistency() {
            User user = createUser(1L, "test");
            int initialHashCode = user.hashCode();

            assertEquals(initialHashCode, user.hashCode());
        }

        private User createUser(Long id, String username) {
            User user = new User() {};
            user.setId(id);
            user.setUsername(username);
            return user;
        }
    }

    @Nested
    class CollectionHandlingTests {
        @Test
        void testEqualsWithDifferentNotificationLists() {
            User user1 = createUserWithNotifications(1);
            User user2 = createUserWithNotifications(2);

            assertEquals(user1, user2);
        }

        @Test
        void testHashCodeWithDifferentNotificationLists() {
            User user1 = createUserWithNotifications(1);
            User user2 = createUserWithNotifications(0);

            assertEquals(user1.hashCode(), user2.hashCode());
        }

        private User createUserWithNotifications(int count) {
            User user = new User() {};
            user.setId(1L);
            for (int i = 0; i < count; i++) {
                user.getNotifications().add(new Message());
            }
            return user;
        }
    }
}