package com.example.volunteer_platform.service;

import com.example.volunteer_platform.exeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.exeptions.InvalidEmailException;
import com.example.volunteer_platform.model.Customer;
import com.example.volunteer_platform.repository.CustomerRepository;
import com.example.volunteer_platform.utils.InputUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerService extends UserService<Customer> {

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        super(customerRepository);
    }

    @Override
    public Customer createUserInstance(String email, String password, String username) {
        Customer customer = Customer.builder()
                                    .email(email)
                                    .password(password)
                                    .username(username)
                                    .build();

        repository.save(customer);

        return customer;
    }

    public Customer createCustomer(String email, String password, String username) {
        if(repository.findUserByEmail(email) != null) {
            if(!InputUtils.isValidEmail(email)) {
                throw new InvalidEmailException("Invalid email format.");
            }

            return createUserInstance(email, password, username);
        }
        throw new EmailAlreadyExistsException(email + " this email is already registered.");
    }
}
