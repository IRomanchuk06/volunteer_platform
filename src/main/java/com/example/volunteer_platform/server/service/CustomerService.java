package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.server.exeptions.InvalidEmailException;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.repository.UserRepository;
import com.example.volunteer_platform.server.utils.CurrentUserContext;
import com.example.volunteer_platform.shared_utils.VerificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service("customerService")
public class CustomerService extends UserService<Customer> {
    private final EventService eventService;

    @Autowired
    public CustomerService(UserRepository userRepository, EventService eventService) {
        super(userRepository);
        this.eventService = eventService;
    }

    @Override
    public Customer createUserInstance(String email, String password, String username) {
        if (repository.findUserByEmail(email) != null) {
            throw new EmailAlreadyExistsException(email + " this email is already registered.");
        }

        if (!VerificationUtils.isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email format.");
        }

        Customer customer = Customer.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();

        repository.save(customer);

        return customer;
    }

    public Event createEvent(String name, String description, String location, LocalDate date,
                             Optional<LocalTime> startTime, Optional<LocalTime> endTime) {
        return eventService.createEvent(name, description, location, date, startTime, endTime,
                CurrentUserContext.getCurrentUser().getEmail());
    }
}
