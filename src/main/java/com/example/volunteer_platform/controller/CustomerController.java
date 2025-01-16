package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.model.Customer;
import com.example.volunteer_platform.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController extends UserController<Customer> {

    @Autowired
    public CustomerController(CustomerService customerService) {
        super(customerService);
    }

    @PostMapping("/")
    public ResponseEntity<Customer> createCustomer(@RequestParam String email, @RequestParam String password,
                                                 @RequestParam String username) {
        CustomerService customerService = (CustomerService) service;
        Customer customer = customerService.createUserInstance(email, password, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customer);
    }
}

