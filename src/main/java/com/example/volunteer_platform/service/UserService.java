package com.example.volunteer_platform.service;

import com.example.volunteer_platform.model.User;
import com.example.volunteer_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userService")
public abstract class UserService<U extends User> {

    protected final UserRepository repository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(repository.findUserByEmail(email));
    }

    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(repository.findUserByUsername(username));
    }

    public User updateUser(String email, String newUsername, String newPassword) {
        User user = repository.findUserByEmail(email);
        if (user != null) {
            user.setUsername(newUsername);
            user.setPassword(newPassword);
            return repository.save(user);
        }
        return null;
    }

    public boolean deleteUser(String email) {
        User user = repository.findUserByEmail(email);
        if (user != null) {
            repository.delete(user);
            return true;
        }
        return false;
    }

    public abstract U createUserInstance(String email, String password, String username);
}
