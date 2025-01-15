package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.model.Customer;
import com.example.volunteer_platform.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController extends UserController<Customer> {

    @Autowired
    public CustomerController(CustomerService customerService) {
        super(customerService);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String username) {
        try {
            CustomerService customerService = (CustomerService) service;
            customerService.createUserInstance(email, password, username);
            return ResponseEntity.status(HttpStatus.CREATED).body("Account successfully created for " + username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
