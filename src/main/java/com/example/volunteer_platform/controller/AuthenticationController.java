package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.model.User;
import com.example.volunteer_platform.service.AuthenticationService;
import com.example.volunteer_platform.utils.CurrentUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        User user = authenticationService.authenticate(email, password);
        CurrentUserContext.setCurrentUser(user);
        return ResponseEntity.ok("Welcome " + user.getUsername());
    }
}


