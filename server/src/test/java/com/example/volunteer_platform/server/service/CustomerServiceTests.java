package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exceptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.server.exceptions.InvalidEmailException;
import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.repository.UserRepository;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventService eventService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MessageService messageService;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(userRepository, eventService, userMapper, messageService);
    }

    @Test
    void testCreateCustomer_Success() {
        String email = "test@example.com";
        String password = "password";
        String username = "testUser";

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setUsername(username);
        customer.setRole(User.UserRole.CUSTOMER);

        when(userRepository.findUserByEmail(email)).thenReturn(null);
        when(userMapper.toUserResponseDTO(customer)).thenReturn(new UserResponseDTO(1L, username, password, email, "CUSTOMER"));

        UserResponseDTO response = customerService.createCustomer(email, password, username);

        assertNotNull(response);
        assertEquals(email, response.getEmail());
        assertEquals(username, response.getUsername());
        assertEquals("CUSTOMER", response.getRole());

        verify(userRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testCreateCustomer_EmailAlreadyExists() {
        String email = "test@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(new Customer());

        assertThrows(EmailAlreadyExistsException.class, () -> {
            customerService.createCustomer(email, "password", "testUser");
        });

        verify(userRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void testCreateCustomer_InvalidEmail() {
        String email = "invalid-email";
        assertThrows(InvalidEmailException.class, () -> {
            customerService.createCustomer(email, "password", "testUser");
        });

        verify(userRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void testCreateEvent_Success() {
        String name = "Event 1";
        String description = "Event Description";
        String location = "Event Location";
        LocalDate date = LocalDate.now();
        Optional<LocalTime> startTime = Optional.of(LocalTime.now());
        Optional<LocalTime> endTime = Optional.of(LocalTime.now().plusHours(2));
        User currentUser = new Customer();
        int numOfRequiredVolunteers = 10;

        EventResponseDTO eventResponseDTO = new EventResponseDTO(1L, name, description, location, date, startTime.orElse(null), endTime.orElse(null), numOfRequiredVolunteers, 0, null, null);

        when(eventService.createEvent(name, description, location, date, startTime, endTime, currentUser, numOfRequiredVolunteers))
                .thenReturn(eventResponseDTO);

        EventResponseDTO response = customerService.createEvent(name, description, location, date, startTime, endTime, currentUser, numOfRequiredVolunteers);

        assertNotNull(response);
        assertEquals(name, response.getName());
        assertEquals(description, response.getDescription());
        assertEquals(location, response.getLocation());

        verify(eventService, times(1)).createEvent(name, description, location, date, startTime, endTime, currentUser, numOfRequiredVolunteers);
    }
}
