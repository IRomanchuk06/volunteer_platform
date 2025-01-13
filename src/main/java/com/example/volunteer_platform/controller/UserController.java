package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.model.User;
import com.example.volunteer_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController<U extends User> {

    protected final UserService<U> service;

    @Autowired
    public UserController(UserService<U> userService) {
        this.service = userService;
    }

    @GetMapping("/email")
    public U getUserByEmail(@RequestParam String email) {
        Optional<U> user = service.getUserByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @GetMapping("/username")
    public U getUserByUsername(@RequestParam String username) {
        Optional<U> user = service.getUserByUsername(username);
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
        return service.updateUser(email, newUsername, newPassword);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String email) {
        service.deleteUser(email);
    }

    public U authenticate(String email, String password) {
        return service.authenticate(email, password);
    }
}
