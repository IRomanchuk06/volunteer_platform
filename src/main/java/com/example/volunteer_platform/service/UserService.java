package com.example.volunteer_platform.service;

import com.example.volunteer_platform.model.User;
import com.example.volunteer_platform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public abstract class UserService<U extends User> {

    private final UserRepository<U> userRepository;

    public UserService(UserRepository<U> userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<U> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findUserByEmail(email));
    }

    public Optional<U> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findUserByUsername(username));
    }

    public U updateUser(String email, String newUsername, String newPassword) {
        U user = userRepository.findUserByEmail(email);
        if (user != null) {
            user.setUsername(newUsername);
            user.setPassword(newPassword);
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(String email) {
        U user = userRepository.findUserByEmail(email);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    protected abstract U createUserInstance(String email, String password, String username);
}
