package com.example.volunteer_platform.server.unit.model;

import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.unit.BaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class VolunteerTests extends BaseUnitTests {
    private Volunteer volunteer;

    @BeforeEach
    void setUp() {
        volunteer = Volunteer.builder().build();
    }

    @Nested
    class DataAnnotationCoverage {
        @Test
        void shouldHandleAllInheritedFieldsThroughBuilder() {
            Volunteer builtVolunteer = Volunteer.builder()
                    .id(1L)
                    .username("test")
                    .password("pass")
                    .email("test@test.com")
                    .role(User.UserRole.VOLUNTEER)
                    .events(new HashSet<>())
                    .build();

            assertAll(
                    () -> assertEquals(1L, builtVolunteer.getId()),
                    () -> assertEquals("test", builtVolunteer.getUsername()),
                    () -> assertEquals("pass", builtVolunteer.getPassword()),
                    () -> assertEquals("test@test.com", builtVolunteer.getEmail()),
                    () -> assertEquals(User.UserRole.VOLUNTEER, builtVolunteer.getRole()),
                    () -> assertNotNull(builtVolunteer.getEvents())
            );
        }

        @Test
        void shouldContainAllInheritedFieldsInToString() {
            Volunteer volunteer = Volunteer.builder()
                    .id(1L)
                    .username("testUser")
                    .build();

            String toStringResult = volunteer.toString();
            assertAll(
                    () -> assertTrue(toStringResult.contains("id=1")),
                    () -> assertTrue(toStringResult.contains("username=testUser")),
                    () -> assertFalse(toStringResult.contains("events="))
            );
        }

        @Test
        void shouldImplementEqualsAndHashCodeCorrectly() {
            Volunteer v1 = Volunteer.builder().id(1L).build();
            Volunteer v2 = Volunteer.builder().id(1L).build();
            v1.getEvents().add(new Event());

            assertAll(
                    () -> assertEquals(v1, v2),
                    () -> assertEquals(v1.hashCode(), v2.hashCode())
            );
        }

        @Test
        void shouldHandlePropertyUpdatesThroughSetters() {
            volunteer.setUsername("newUsername");
            volunteer.setEmail("new@email.com");

            assertAll(
                    () -> assertEquals("newUsername", volunteer.getUsername()),
                    () -> assertEquals("new@email.com", volunteer.getEmail())
            );
        }

        @Test
        void shouldImplementLombokConstructors() {
            Volunteer emptyVolunteer = new Volunteer();
            assertNotNull(emptyVolunteer);

            Volunteer builtVolunteer = Volunteer.builder().build();
            assertNotNull(builtVolunteer);
        }
    }

    @Nested
    class CollectionHandlingTests {
        @Test
        void shouldInitializeEmptyEventsSetByDefault() {
            Volunteer newVolunteer = new Volunteer();
            assertTrue(newVolunteer.getEvents().isEmpty());
        }

        @Test
        void shouldAllowFullCollectionReplacement() {
            Set<Event> newEvents = new HashSet<>();
            newEvents.add(new Event());
            volunteer.setEvents(newEvents);

            assertEquals(1, volunteer.getEvents().size());
        }
    }
}