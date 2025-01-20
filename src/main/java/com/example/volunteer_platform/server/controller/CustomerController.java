package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.dto.RegistrationRequest;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

