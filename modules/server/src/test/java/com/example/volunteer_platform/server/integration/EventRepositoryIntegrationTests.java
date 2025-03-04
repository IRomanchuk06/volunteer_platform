package com.example.volunteer_platform.server.integration;

import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.repository.EventRepository;
import com.example.volunteer_platform.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventRepositoryIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testExistsByNameAndDate() {
        Customer customer = new Customer();
        customer.setEmail("customer@example.com");
        customer.setUsername("customer");
        customer.setPassword("password");
        customer.setRole(User.UserRole.CUSTOMER);
        userRepository.save(customer);

        Event event = new Event();
        event.setName("Test Event");
        event.setDate(LocalDate.now());
        event.setDescription("Test Description");
        event.setLocation("Test Location");
        event.setNumOfRequiredVolunteers(5);
        event.setCustomer(customer);
        eventRepository.save(event);

        boolean exists = eventRepository.existsByNameAndDate("Test Event", LocalDate.now());

        assertThat(exists).isTrue();
    }

    @Test
    void testGetRespondingVolunteersCount() {
        Customer customer = new Customer();
        customer.setEmail("customer@example.com");
        customer.setUsername("customer");
        customer.setPassword("password");
        customer.setRole(User.UserRole.CUSTOMER);
        userRepository.save(customer);

        Volunteer volunteer = new Volunteer();
        volunteer.setEmail("volunteer@example.com");
        volunteer.setUsername("volunteer");
        volunteer.setPassword("password");
        volunteer.setRole(User.UserRole.VOLUNTEER);
        userRepository.save(volunteer);

        Event event = new Event();
        event.setName("Test Event");
        event.setDate(LocalDate.now());
        event.setDescription("Test Description");
        event.setLocation("Test Location");
        event.setNumOfRequiredVolunteers(5);
        event.setCustomer(customer);
        event.setVolunteers(new HashSet<>());
        event.getVolunteers().add((Volunteer) volunteer);
        eventRepository.save(event);

        int count = eventRepository.getRespondingVolunteersCount(event.getId());

        assertThat(count).isEqualTo(1);
    }

    @Test
    void testAddVolunteerToEvent() {
        Customer customer = new Customer();
        customer.setEmail("customer@example.com");
        customer.setUsername("customer");
        customer.setPassword("password");
        customer.setRole(User.UserRole.CUSTOMER);
        userRepository.save(customer);

        Volunteer volunteer = new Volunteer();
        volunteer.setEmail("volunteer@example.com");
        volunteer.setUsername("volunteer");
        volunteer.setPassword("password");
        volunteer.setRole(User.UserRole.VOLUNTEER);
        userRepository.save(volunteer);

        Event event = new Event();
        event.setName("Test Event");
        event.setDate(LocalDate.now());
        event.setDescription("Test Description");
        event.setLocation("Test Location");
        event.setNumOfRequiredVolunteers(5);
        event.setCustomer(customer);
        event.setVolunteers(new HashSet<>());
        eventRepository.save(event);

        eventRepository.addVolunteerToEvent(event.getId(), volunteer.getId());

        eventRepository.flush();
        Event updatedEvent = eventRepository.findById(event.getId()).orElseThrow();

        assertThat(updatedEvent.getVolunteers()).hasSize(1);
    }
}