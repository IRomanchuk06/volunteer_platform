package com.example.volunteer_platform.service;

import com.example.volunteer_platform.exeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.exeptions.InvalidEmailException;
import com.example.volunteer_platform.model.Customer;
import com.example.volunteer_platform.repository.UserRepository;
import com.example.volunteer_platform.utils.InputUtils;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerService extends UserService<Customer> {

    public CustomerService(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public void createUserInstance(String email, String password, String username) {
        if(repository.findUserByEmail(email) != null) {
            throw new EmailAlreadyExistsException(email + " this email is already registered.");
        }

        if(!InputUtils.isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email format.");
        }

        Customer customer = Customer.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();

        repository.save(customer);
    }
}
