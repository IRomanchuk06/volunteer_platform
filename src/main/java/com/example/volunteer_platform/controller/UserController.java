package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.model.User;
import com.example.volunteer_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public abstract class UserController<U extends User> {

    protected final UserService<U> service;

    @Autowired
    public UserController(UserService<U> userService) {
        this.service = userService;
    }

    @GetMapping("/email")
    public ResponseEntity<U> getUserByEmail(@RequestParam String email) {
        Optional<U> user = service.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("/username")
    public ResponseEntity<U> getUserByUsername(@RequestParam String username) {
        Optional<U> user = service.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PutMapping("/update")
    public ResponseEntity<U> updateUser(@RequestParam String email,
                                        @RequestParam String newUsername,
                                        @RequestParam String newPassword) {
        U updatedUser = service.updateUser(email, newUsername, newPassword);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
        boolean isDeleted = service.deleteUser(email);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }
}
