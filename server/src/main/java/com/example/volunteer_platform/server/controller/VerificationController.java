package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.exсeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.server.exсeptions.InvalidEmailException;
import com.example.volunteer_platform.server.service.VerificationService;
import com.example.volunteer_platform.server.logging.AppLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification")
public class VerificationController {

    private static final Logger logger = AppLogger.SERVER_LOGGER;

    private final VerificationService verificationService;

    @Autowired
    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping("/emails/valid")
    public ResponseEntity<String> verifyValidEmail(@RequestParam String email) {
        logger.info("Incoming request to /verification/emails/valid with email: {}", email);

        if (!verificationService.isValidEmail(email)) {
            logger.warn("Invalid email format: {}", email);
            throw new InvalidEmailException("Invalid email format: " + email);
        }

        logger.info("Email is valid: {}", email);
        return ResponseEntity.ok("Email is valid.");
    }

    @GetMapping("/emails/available")
    public ResponseEntity<String> verifyAvailableEmail(@RequestParam String email) {
        logger.info("Incoming request to /verification/emails/available with email: {}", email);

        if (!verificationService.isAvailableEmail(email)) {
            logger.warn("Email {} is already taken", email);
            throw new EmailAlreadyExistsException("Email " + email + " is already taken");
        }

        logger.info("Email is available: {}", email);
        return ResponseEntity.ok("Email is available.");
    }
}
