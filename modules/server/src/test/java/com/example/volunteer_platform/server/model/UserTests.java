package com.example.volunteer_platform.server.model;

import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(User.UserRole.CUSTOMER, user.getRole());
    }

    @Test
    void testNotifications() {
        Notification notification = new Message();
        user.getNotifications().add(notification);

        assertEquals(1, user.getNotifications().size());
        assertTrue(user.getNotifications().contains(notification));
    }

    @Test
    void testAllArgsConstructor() {
        List<Notification> notifications = new ArrayList<>();

        User user = new User(1L, "testUser", "testPassword", "test@example.com", User.UserRole.CUSTOMER, notifications) {};

        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(User.UserRole.CUSTOMER, user.getRole());
        assertEquals(notifications, user.getNotifications());
    }

    @Test
    void testAllArgsConstructorWithNullValues() {
        List<Notification> notifications = new ArrayList<>();

        User user = new User(null, null, null, null, null, notifications) {};

        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertNull(user.getRole());
        assertEquals(notifications, user.getNotifications());
    }

    @Test
    void testAddNullNotification() {
        user.getNotifications().add(null);

        assertEquals(1, user.getNotifications().size());
        assertNull(user.getNotifications().getFirst());
    }

    @Test
    void testRemoveNotification() {
        Notification notification = new Message();
        user.getNotifications().add(notification);

        assertEquals(1, user.getNotifications().size());
        assertTrue(user.getNotifications().contains(notification));

        user.getNotifications().remove(notification);

        assertEquals(0, user.getNotifications().size());
        assertFalse(user.getNotifications().contains(notification));
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User(1L, "testUser", "testPassword", "test@example.com", User.UserRole.CUSTOMER, new ArrayList<>()) {};
        User user2 = new User(1L, "testUser", "testPassword", "test@example.com", User.UserRole.CUSTOMER, new ArrayList<>()) {};

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testNotEquals() {
        User user1 = new User(1L, "testUser", "testPassword", "test@example.com", User.UserRole.CUSTOMER, new ArrayList<>()) {};
        User user2 = new User(2L, "testUser2", "testPassword2", "test2@example.com", User.UserRole.VOLUNTEER, new ArrayList<>()) {};

        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}