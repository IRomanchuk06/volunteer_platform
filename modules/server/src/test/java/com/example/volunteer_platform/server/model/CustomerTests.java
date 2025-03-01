package com.example.volunteer_platform.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

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

    @Nested
    class EqualsAndHashCodeTests {
        @Test
        void shouldBeEqualWhenSameId() {
            Customer customer1 = Customer.builder().id(1L).build();
            Customer customer2 = Customer.builder().id(1L).build();

            assertEquals(customer1, customer2);
            assertEquals(customer1.hashCode(), customer2.hashCode());
        }

        @Test
        void shouldNotBeEqualWhenDifferentIds() {
            Customer customer1 = Customer.builder().id(1L).build();
            Customer customer2 = Customer.builder().id(2L).build();

            assertNotEquals(customer1, customer2);
            assertNotEquals(customer1.hashCode(), customer2.hashCode());
        }

        @Test
        void shouldNotBeEqualWhenComparedWithNull() {
            Customer customer = Customer.builder().id(1L).build();
            assertNotEquals(null, customer);
        }

        @Test
        void shouldNotBeEqualWhenComparedWithDifferentClass() {
            Customer customer = Customer.builder().id(1L).build();
            assertNotEquals(new Object(), customer);
        }

        @Test
        void shouldIgnoreEventsInEqualsAndHashCode() {
            Set<Event> events1 = new HashSet<>();
            events1.add(new Event());

            Set<Event> events2 = new HashSet<>();

            Customer customer1 = Customer.builder().id(1L).events(events1).build();
            Customer customer2 = Customer.builder().id(1L).events(events2).build();

            assertEquals(customer1, customer2);
            assertEquals(customer1.hashCode(), customer2.hashCode());
        }
    }

    @Nested
    class BuilderTests {
        @Test
        void shouldBuildCustomerWithAllFields() {
            Set<Event> events = new HashSet<>();
            events.add(new Event());

            Customer customer = Customer.builder()
                    .id(1L)
                    .username("testUser")
                    .password("testPassword")
                    .email("test@example.com")
                    .role(User.UserRole.CUSTOMER)
                    .events(events)
                    .build();

            assertAll(
                    () -> assertEquals(1L, customer.getId()),
                    () -> assertEquals("testUser", customer.getUsername()),
                    () -> assertEquals("testPassword", customer.getPassword()),
                    () -> assertEquals("test@example.com", customer.getEmail()),
                    () -> assertEquals(User.UserRole.CUSTOMER, customer.getRole()),
                    () -> assertEquals(1, customer.getEvents().size())
            );
        }

        @Test
        void shouldBuildCustomerWithPartialFields() {
            Customer customer = Customer.builder()
                    .username("partialUser")
                    .build();

            assertAll(
                    () -> assertNull(customer.getId()),
                    () -> assertEquals("partialUser", customer.getUsername()),
                    () -> assertNull(customer.getPassword()),
                    () -> assertNull(customer.getEmail()),
                    () -> assertNull(customer.getRole()),
                    () -> assertTrue(customer.getEvents().isEmpty())
            );
        }
    }
}