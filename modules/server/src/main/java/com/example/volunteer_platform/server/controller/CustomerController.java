package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.CustomerService;
import com.example.volunteer_platform.shared_dto.EventRegistrationDTO;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.UserRegistrationDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CustomerController extends UserController {

    @Autowired
    public CustomerController(CustomerService customerService) {
        super(customerService);
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> createCustomer(@Valid @RequestBody UserRegistrationDTO accountRequest) {
        CustomerService customerService = (CustomerService) userService;

        serverLogger.info("Received request to create customer with email: {}", accountRequest.getEmail());

        UserResponseDTO userResponse = customerService.createCustomer(
                accountRequest.getEmail(),
                accountRequest.getPassword(),
                accountRequest.getUsername());

        serverLogger.info("Customer created successfully: {}", accountRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/events/")
    public ResponseEntity<EventResponseDTO> createEvent(@Valid @RequestBody EventRegistrationDTO eventRequest, HttpServletRequest request) {
        serverLogger.info("Incoming request to /customers/events with payload: {}", eventRequest);

        User currentUser = getUserFromSession(request);

        CustomerService customerService = (CustomerService) userService;
        EventResponseDTO eventResponse = customerService.createEvent(
                eventRequest.getName(),
                eventRequest.getDescription(),
                eventRequest.getLocation(),
                eventRequest.getDate(),
                Optional.ofNullable(eventRequest.getStartTime()),
                Optional.ofNullable(eventRequest.getEndTime()),
                currentUser,
                eventRequest.getNumOfRequiredVolunteers()
        );

        serverLogger.info("Responding with status {} and payload: {}", HttpStatus.CREATED, eventResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponse);
    }
}
