package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.exeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.exeptions.InvalidEmailException;
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

    @PostMapping("/create")
    public void createCustomer(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String username) {
        try {
            CustomerService customerService = (CustomerService) service;
            customerService.createCustomer(email, password, username);
        } catch (EmailAlreadyExistsException | InvalidEmailException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }
}
