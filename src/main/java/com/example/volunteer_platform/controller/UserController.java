package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.model.User;
import com.example.volunteer_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public abstract class UserController<U extends User> {

    protected final UserService<U> userService;

    @Autowired
    public UserController(UserService<U> userService) {
        this.userService = userService;
    }

    @GetMapping("/email")
    public U getUserByEmail(@RequestParam String email) {
        Optional<U> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @GetMapping("/username")
    public U getUserByUsername(@RequestParam String username) {
        Optional<U> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @PutMapping("/update")
    public U updateUser(@RequestParam String email,
                        @RequestParam String newUsername,
                        @RequestParam String newPassword) {
        return userService.updateUser(email, newUsername, newPassword);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String email) {
        userService.deleteUser(email);
    }

    protected abstract U createUserInstance(String email, String password, String username);
}
