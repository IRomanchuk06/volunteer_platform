package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.model.User;
import com.example.volunteer_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public abstract class UserController<U extends User> {

    protected final UserService<U> service;

    @Autowired
    public UserController(UserService<U> userService) {
        this.service = userService;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = service.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = service.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username: " + username));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam String email,
                                        @RequestParam String newUsername,
                                        @RequestParam String newPassword) {
        User updatedUser = service.updateUser(email, newUsername, newPassword);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        boolean isDeleted = service.deleteUser(email);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }
    }
}
