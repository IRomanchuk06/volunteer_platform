package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.mapper.EventMapper;
import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.CustomerService;
import com.example.volunteer_platform.shared_dto.EventRegistrationDTO;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.UserRegistrationDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import static com.example.volunteer_platform.server.utils.SessionUtils.getUserFromSession;

@RestController
@RequestMapping("/customers")
public class CustomerController extends UserController<Customer> {

    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    @Autowired
    public CustomerController(CustomerService customerService, EventMapper eventMapper, UserMapper userMapper) {
        super(customerService, userMapper);
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> createCustomer(@Valid @RequestBody UserRegistrationDTO accountRequest) {
        CustomerService customerService = (CustomerService) service;
        Customer customer = customerService.createUserInstance(
                accountRequest.getEmail(),
                accountRequest.getPassword(),
                accountRequest.getUsername());

        UserResponseDTO response = userMapper.toUserResponseDTO(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/events/")
    public ResponseEntity<EventResponseDTO> createEvent(@Valid @RequestBody EventRegistrationDTO eventRequest, HttpServletRequest request) {
        User currentUser = getUserFromSession(request);

        CustomerService customerService = (CustomerService) service;

        Event event = customerService.createEvent(
                eventRequest.getName(),
                eventRequest.getDescription(),
                eventRequest.getLocation(),
                eventRequest.getDate(),
                Optional.ofNullable(eventRequest.getStartTime()),
                Optional.ofNullable(eventRequest.getEndTime()),
                currentUser,
                eventRequest.getNumOfRequiredVolunteers()
                );

        EventResponseDTO eventResponse = eventMapper.toResponseDTO(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponse);
    }
}

