package com.example.volunteer_platform.service;

import com.example.volunteer_platform.model.Customer;
import com.example.volunteer_platform.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends UserService<Customer> {

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        super(customerRepository);
    }

    public Customer createCustomer(String email, String password, String username) {
        return createUserInstance(email, password, username);
    }

    @Override
    protected Customer createUserInstance(String email, String password, String username) {
        return new Customer(email, password, username);
    }
}
