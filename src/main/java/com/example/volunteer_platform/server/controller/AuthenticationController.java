package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.dto.LoginRequest;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.AuthenticationService;
import com.example.volunteer_platform.server.utils.CurrentUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        User user = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        CurrentUserContext.setCurrentUser(user);
        return ResponseEntity.ok("Welcome " + user.getUsername());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        CurrentUserContext.clear();
        return ResponseEntity.ok("Logged out successfully");
    }
}


