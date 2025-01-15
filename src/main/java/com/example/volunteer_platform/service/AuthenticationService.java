package com.example.volunteer_platform.service;

import com.example.volunteer_platform.exeptions.AuthenticationException;
import com.example.volunteer_platform.model.Customer;
import com.example.volunteer_platform.model.User;
import com.example.volunteer_platform.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationService {

    private final UserService<Volunteer> volunteerService;
    private final UserService<Customer> customerService;

    @Autowired
    public AuthenticationService(UserService<Volunteer> volunteerService, UserService<Customer> customerService) {
        this.volunteerService = volunteerService;
        this.customerService = customerService;
    }

    public User authenticate(String email, String password) {
        Optional<Volunteer> volunteer = volunteerService.getUserByEmail(email);
        if (volunteer.isPresent() && volunteer.get().getPassword().equals(password)) {
            return volunteer.get();
        }

        Optional<Customer> customer = customerService.getUserByEmail(email);
        if (customer.isPresent() && customer.get().getPassword().equals(password)) {
            return customer.get();
        }

        throw new AuthenticationException("Invalid email or password");
    }
}
