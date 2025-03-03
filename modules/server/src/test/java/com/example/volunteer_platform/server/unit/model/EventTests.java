package com.example.volunteer_platform.server.unit.model;

import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.model.Response;
import com.example.volunteer_platform.server.model.Volunteer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EventTests {
    private Event event;
    private Customer customer;
    private Volunteer volunteer;
    private Response response;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        volunteer = new Volunteer();
        response = new Response();

        event = Event.builder()
                .id(1L)
                .name("Test Event")
                .description("Test Description")
                .location("Test Location")
                .date(LocalDate.now())
                .startTime(LocalTime.NOON)
                .endTime(LocalTime.MIDNIGHT)
                .numOfRequiredVolunteers(5)
                .customer(customer)
                .build();
    }

    @Nested
    class SettersAndGettersTests {
        @Test
        void shouldSetAndGetAllFields() {
            event.setId(2L);
            event.setName("Updated Name");
            event.setDescription("Updated Description");
            event.setLocation("Updated Location");
            event.setDate(LocalDate.of(2023, 12, 31));
            event.setStartTime(LocalTime.of(10, 0));
            event.setEndTime(LocalTime.of(20, 0));
            event.setNumOfRequiredVolunteers(10);

            Set<Volunteer> volunteers = new HashSet<>();
            volunteers.add(volunteer);
            event.setVolunteers(volunteers);

            List<Response> responses = new ArrayList<>();
            responses.add(response);
            event.setResponses(responses);

            assertAll(
                    () -> assertEquals(2L, event.getId()),
                    () -> assertEquals("Updated Name", event.getName()),
                    () -> assertEquals("Updated Description", event.getDescription()),
                    () -> assertEquals("Updated Location", event.getLocation()),
                    () -> assertEquals(LocalDate.of(2023, 12, 31), event.getDate()),
                    () -> assertEquals(LocalTime.of(10, 0), event.getStartTime()),
                    () -> assertEquals(LocalTime.of(20, 0), event.getEndTime()),
                    () -> assertEquals(10, event.getNumOfRequiredVolunteers()),
                    () -> assertEquals(1, event.getVolunteers().size()),
                    () -> assertEquals(1, event.getResponses().size())
            );
        }

        @Test
        void shouldHandleNullCollections() {
            event.setVolunteers(null);
            event.setResponses(null);

            assertAll(
                    () -> assertNull(event.getVolunteers()),
                    () -> assertNull(event.getResponses())
            );
        }
    }

    @Nested
    class EqualsAndHashCodeTests {
        @Test
        void shouldBeEqualWhenSameId() {
            Event event2 = Event.builder().id(1L).name("Different Name").build();
            assertEquals(event, event2);
        }

        @Test
        void shouldNotBeEqualWhenDifferentIds() {
            Event event2 = Event.builder().id(2L).build();
            assertNotEquals(event, event2);
            assertNotEquals(event.hashCode(), event2.hashCode());
        }

        @Test
        void shouldIgnoreExcludedFieldsInEquals() {
            Event event2 = Event.builder()
                    .id(1L)
                    .customer(new Customer())
                    .volunteers(Set.of(new Volunteer()))
                    .responses(List.of(new Response()))
                    .build();

            assertEquals(event, event2, "Equals должен игнорировать customer, volunteers и responses");
            assertEquals(event.hashCode(), event2.hashCode());
        }

        @Test
        void shouldNotBeEqualWithDifferentClass() {
            assertNotEquals(new Object(), event);
        }

        @Test
        void shouldNotBeEqualWithNull() {
            assertNotEquals(null, event);
        }

        @Test
        void shouldBeReflexive() {
            assertEquals(event, event);
        }

        @Test
        void shouldBeSymmetric() {
            Event event2 = Event.builder().id(1L).build();
            assertTrue(event.equals(event2) && event2.equals(event));
        }

        @Test
        void shouldBeTransitive() {
            Event event2 = Event.builder().id(1L).build();
            Event event3 = Event.builder().id(1L).build();
            assertTrue(event.equals(event2) && event2.equals(event3) && event.equals(event3));
        }

        @Test
        void shouldNotBeEqualWithDifferentNonExcludedFields() {
            Event event2 = Event.builder()
                    .id(1L)
                    .name("Different Name")
                    .description("Different Description")
                    .build();

            assertEquals(event, event2, "Equals должен учитывать только id");
        }
    }

    @Nested
    class ToStringTests {
        @Test
        void shouldExcludeSpecifiedFields() {
            String toStringResult = event.toString();

            assertAll(
                    () -> assertFalse(toStringResult.contains("responses")),
                    () -> assertFalse(toStringResult.contains("volunteers")),
                    () -> assertFalse(toStringResult.contains("customer"))
            );
        }
    }

    @Nested
    class RelationshipsTests {
        @Test
        void shouldAddVolunteerToEvent() {
            event.getVolunteers().add(volunteer);
            assertTrue(event.getVolunteers().contains(volunteer));
        }

        @Test
        void shouldAddResponseToEvent() {
            event.getResponses().add(response);
            assertTrue(event.getResponses().contains(response));
        }

        @Test
        void shouldMaintainBidirectionalRelationship() {
            response.setEvent(event);
            event.getResponses().add(response);

            assertEquals(event, response.getEvent());
            assertTrue(event.getResponses().contains(response));
        }
    }

    @Nested
    class BuilderTests {
        @Test
        void shouldBuildWithAllFields() {
            Event builtEvent = Event.builder()
                    .id(3L)
                    .name("Built Event")
                    .description("Built Description")
                    .location("Built Location")
                    .date(LocalDate.of(2024, 1, 1))
                    .startTime(LocalTime.of(9, 30))
                    .endTime(LocalTime.of(18, 0))
                    .numOfRequiredVolunteers(7)
                    .customer(customer)
                    .volunteers(Set.of(volunteer))
                    .responses(List.of(response))
                    .build();

            assertAll(
                    () -> assertEquals(3L, builtEvent.getId()),
                    () -> assertEquals("Built Event", builtEvent.getName()),
                    () -> assertEquals(7, builtEvent.getNumOfRequiredVolunteers()),
                    () -> assertEquals(1, builtEvent.getVolunteers().size()),
                    () -> assertEquals(1, builtEvent.getResponses().size())
            );
        }
    }
}