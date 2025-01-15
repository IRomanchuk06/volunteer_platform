package com.example.volunteer_platform.service;

import com.example.volunteer_platform.exeptions.AuthenticationException;
import com.example.volunteer_platform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationService {

    private final UserService<User> userService;

    @Autowired
    public AuthenticationService(UserService<User> userService) {
        this.userService = userService;
    }

    public User authenticate(String email, String password) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }

        throw new AuthenticationException("Invalid email or password");
    }
}
