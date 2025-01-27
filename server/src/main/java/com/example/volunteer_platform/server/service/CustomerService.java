package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.server.exeptions.InvalidEmailException;
import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.repository.UserRepository;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import com.example.volunteer_platform.shared_utils.VerificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service("customerService")
public class CustomerService extends UserService<Customer> {
    private final EventService eventService;
    private final UserMapper userMapper;

    @Autowired
    public CustomerService(UserRepository userRepository, EventService eventService, UserMapper userMapper) {
        super(userRepository, userMapper);
        this.eventService = eventService;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO createUserInstance(String email, String password, String username) {
        if (userRepository.findUserByEmail(email) != null) {
            throw new EmailAlreadyExistsException(email + " this email is already registered.");
        }

        if (!VerificationUtils.isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email format.");
        }

        Customer customer = Customer.builder().email(email).password(password).username(username).role(
                "CUSTOMER").build();

        userRepository.save(customer);

        return userMapper.toUserResponseDTO(customer);
    }

    public EventResponseDTO createEvent(String name, String description, String location, LocalDate date,
                                        Optional<LocalTime> startTime, Optional<LocalTime> endTime, User currentUser,
                                        int numOfRequiredVolunteers) {
        return eventService.createEvent(name, description, location, date, startTime, endTime, currentUser, numOfRequiredVolunteers);
    }
}
