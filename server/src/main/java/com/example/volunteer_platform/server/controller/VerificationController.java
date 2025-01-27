package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.exeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.server.exeptions.InvalidEmailException;
import com.example.volunteer_platform.server.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            throw new InvalidEmailException("Invalid email format: " + email);
        }
        return ResponseEntity.ok("Email is valid.");
    }

    @GetMapping("/emails/available")
    public ResponseEntity<String> verifyAvailableEmail(@RequestParam String email) {
        if (!verificationService.isAvailableEmail(email)) {
            throw new EmailAlreadyExistsException("Email " + email + " is already taken");
        }
        return ResponseEntity.ok("Email is available.");
    }
}

