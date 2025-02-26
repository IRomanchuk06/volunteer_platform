package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exceptions.AuthenticationException;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationService {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String email, String password) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(email));
        if (user.isPresent()) {
            if(user.get().getPassword().equals(password)) {
                return user.get();
            }
            throw new AuthenticationException("Invalid password");
        }
        throw new AuthenticationException("Invalid email");
    }
}
