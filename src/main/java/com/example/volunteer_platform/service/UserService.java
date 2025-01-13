package com.example.volunteer_platform.service;

import com.example.volunteer_platform.model.User;
import com.example.volunteer_platform.repository.UserRepository;
import com.example.volunteer_platform.utils.CurrentUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public abstract class UserService<U extends User> {

    protected final UserRepository<U> repository;

    @Autowired
    public UserService(UserRepository<U> userRepository) {
        this.repository = userRepository;
    }

    public Optional<U> getUserByEmail(String email) {
        return Optional.ofNullable(repository.findUserByEmail(email));
    }

    public Optional<U> getUserByUsername(String username) {
        return Optional.ofNullable(repository.findUserByUsername(username));
    }

    public U updateUser(String email, String newUsername, String newPassword) {
        U user = repository.findUserByEmail(email);
        if (user != null) {
            user.setUsername(newUsername);
            user.setPassword(newPassword);
            return repository.save(user);
        }
        return null;
    }

    public void deleteUser(String email) {
        U user = repository.findUserByEmail(email);
        if (user != null) {
            repository.delete(user);
        }
    }

    public U authenticate(String email, String password) {
        U user = repository.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            CurrentUserContext.setCurrentUser(user);
            return user;
        }
        return null;
    }

    public abstract U createUserInstance(String email, String password, String username);
}
