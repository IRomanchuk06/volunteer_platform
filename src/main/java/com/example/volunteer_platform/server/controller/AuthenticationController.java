package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.dto.LoginRequest;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.AuthenticationService;
import com.example.volunteer_platform.server.utils.CurrentUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        User user = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        CurrentUserContext.setCurrentUser(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome " + user.getUsername());
        response.put("role", user.getRole());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        CurrentUserContext.clear();
        return ResponseEntity.ok("Logged out successfully");
    }
}


