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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        eventRepository.deleteAll();
    }

    @Test
    void testFindUserByEmail() {
        Volunteer volunteer = Volunteer.builder()
                .email("test@example.com")
                .username("testuser")
                .password("password")
                .role(User.UserRole.VOLUNTEER)
                .build();
        userRepository.save(volunteer);

        User foundUser = userRepository.findUserByEmail("test@example.com");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
        assertThat(foundUser).isInstanceOf(Volunteer.class);
    }

    @Test
    void testFindUserByUsername() {
        Customer customer = Customer.builder()
                .email("customer@example.com")
                .username("testcustomer")
                .password("password")
                .role(User.UserRole.CUSTOMER)
                .build();
        userRepository.save(customer);

        User foundUser = userRepository.findUserByUsername("testcustomer");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testcustomer");
        assertThat(foundUser).isInstanceOf(Customer.class);
    }

    @Test
    void testExistsUserByEmail() {
        Volunteer volunteer = Volunteer.builder()
                .email("exists@example.com")
                .username("existuser")
                .password("password")
                .role(User.UserRole.VOLUNTEER)
                .build();
        userRepository.save(volunteer);

        boolean exists = userRepository.existsUserByEmail("exists@example.com");
        boolean notExists = userRepository.existsUserByEmail("wrong@example.com");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    void testExistsByEventIdAndVolunteerId() {
        Customer customer = Customer.builder()
                .email("customer@example.com")
                .username("customer")
                .password("password")
                .role(User.UserRole.CUSTOMER)
                .build();
        userRepository.save(customer);

        Volunteer volunteer = Volunteer.builder()
                .email("volunteer@example.com")
                .username("volunteer")
                .password("password")
                .role(User.UserRole.VOLUNTEER)
                .build();
        userRepository.save(volunteer);

        Event event = Event.builder()
                .name("Test Event")
                .description("This is a test event")
                .location("Test Location")
                .date(LocalDate.now())
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(12, 0))
                .numOfRequiredVolunteers(5)
                .customer(customer)
                .volunteers(new HashSet<>())
                .build();
        eventRepository.save(event);

        event.getVolunteers().add(volunteer);
        eventRepository.save(event);

        boolean exists = userRepository.existsByEventIdAndVolunteerId(event.getId(), volunteer.getId());
        assertThat(exists).isTrue();

        boolean notExists = userRepository.existsByEventIdAndVolunteerId(999L, volunteer.getId());
        assertThat(notExists).isFalse();
    }
}