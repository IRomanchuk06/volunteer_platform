package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/verification")
public class VerificationController {

    private final VerificationService verificationService;

    @Autowired
    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping("/emails/valid")
    public ResponseEntity<String> verifyValidEmail(@RequestParam String email) {
        if (!verificationService.isValidEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }
        return ResponseEntity.ok("Email is valid.");
    }

    @GetMapping("/emails/available")
    public ResponseEntity<String> verifyAvailableEmail(@RequestParam String email) {
        if (!verificationService.isAvailableEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email " + email + " is already taken");
        }
        return ResponseEntity.ok("Email is available.");
    }
}

