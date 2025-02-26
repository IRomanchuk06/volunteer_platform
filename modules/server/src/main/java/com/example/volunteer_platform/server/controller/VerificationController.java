package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.exceptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.server.exceptions.InvalidEmailException;
import com.example.volunteer_platform.server.service.VerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification")
public class VerificationController {

    private static final Logger serverLogger = LoggerFactory.getLogger(VerificationController.class);
    private final VerificationService verificationService;

    @Autowired
    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping("/emails/valid")
    public ResponseEntity<String> verifyValidEmail(@RequestParam String email) {
        serverLogger.info("Incoming request to /verification/emails/valid with email: {}", email);

        if (!verificationService.isValidEmail(email)) {
            serverLogger.warn("Invalid email format: {}", email);
            throw new InvalidEmailException("Invalid email format: " + email);
        }

        serverLogger.info("Email is valid: {}", email);
        return ResponseEntity.ok("Email is valid.");
    }

    @GetMapping("/emails/available")
    public ResponseEntity<String> verifyAvailableEmail(@RequestParam String email) {
        serverLogger.info("Incoming request to /verification/emails/available with email: {}", email);

        if (!verificationService.isAvailableEmail(email)) {
            serverLogger.warn("Email {} is already taken", email);
            throw new EmailAlreadyExistsException("Email " + email + " is already taken");
        }

        serverLogger.info("Email is available: {}", email);
        return ResponseEntity.ok("Email is available.");
    }
}
