package com.example.volunteer_platform.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTests {
    private Customer customer;
    private Event event;

    @BeforeEach
    void setUp() {
        customer = Customer.builder().build();
        event = new Event();
    }

    @Nested
    class EventsRelationshipTests {
        @Test
        void shouldAddEventToCustomer() {
            customer.getEvents().add(event);
            assertEquals(1, customer.getEvents().size());
            assertTrue(customer.getEvents().contains(event));
        }

        @Test
        void shouldRemoveEventFromCustomer() {
            customer.getEvents().add(event);
            customer.getEvents().remove(event);
            assertEquals(0, customer.getEvents().size());
        }

        @Test
        void shouldClearAllEvents() {
            customer.getEvents().add(event);
            customer.getEvents().clear();
            assertEquals(0, customer.getEvents().size());
        }

        @Test
        void shouldMaintainBidirectionalRelationship() {
            customer.getEvents().add(event);
            event.setCustomer(customer);

            assertEquals(customer, event.getCustomer());
            assertTrue(customer.getEvents().contains(event));
        }
    }

    @Nested
    class LombokAnnotationsTests {
        @Test
        void shouldHandleAllGettersAndSetters() {
            customer.setId(3L);
            customer.setUsername("customerUser");
            assertEquals(3L, customer.getId());
            assertEquals("customerUser", customer.getUsername());
        }

        @Test
        void shouldHandleNullEvents() {
            customer.setEvents(null);
            assertNull(customer.getEvents());
        }
    }
}