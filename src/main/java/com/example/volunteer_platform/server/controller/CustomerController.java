package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.dto.EventRequest;
import com.example.volunteer_platform.server.dto.RegistrationRequest;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import java.util.Optional;

import static com.example.volunteer_platform.server.utils.SessionUtils.getUserFromSession;

@RestController
@RequestMapping("/customers")
public class CustomerController extends UserController<Customer> {

    @Autowired
    public CustomerController(CustomerService customerService) {
        super(customerService);
    }

    @PostMapping("/")
    public ResponseEntity<Customer> createCustomer(@RequestBody RegistrationRequest accountRequest) {
        CustomerService customerService = (CustomerService) service;
        Customer customer = customerService.createUserInstance(
                accountRequest.getEmail(),
                accountRequest.getPassword(),
                accountRequest.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody EventRequest eventRequest, HttpServletRequest request) {
        User currentUser = getUserFromSession(request);

        CustomerService customerService = (CustomerService) service;

        String dateStr = eventRequest.getDate();
        String startTimeStr = eventRequest.getStartTime();
        String endTimeStr = eventRequest.getEndTime();

        Event event = customerService.createEvent(
                eventRequest.getName(),
                eventRequest.getDescription(),
                eventRequest.getLocation(),
                LocalDate.parse(dateStr),
                Optional.of(LocalTime.parse(startTimeStr)),
                Optional.of(LocalTime.parse(endTimeStr)),
                currentUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}

