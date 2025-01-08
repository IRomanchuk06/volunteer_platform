package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.model.Customer;
import com.example.volunteer_platform.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController extends UserController<Customer> {

    @Autowired
    public CustomerController(CustomerService customerService) {
        super(customerService);
    }

    @Override
    protected Customer createUserInstance(String email, String password, String username) {
        return new Customer(email, password, username);
    }
}
