package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.VolunteerService;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.UserRegistrationDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.volunteer_platform.server.utils.SessionUtils.getUserFromSession;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController extends UserController {

    private static final Logger logger = LoggerFactory.getLogger(VolunteerController.class);

    private final VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        super(volunteerService);  // Assuming the base class handles basic user-related functionalities
        this.volunteerService = volunteerService;
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> createVolunteer(@Valid @RequestBody UserRegistrationDTO accountRequest) {
        logger.info("Received request to create volunteer with email: {}", accountRequest.getEmail());

        try {
            UserResponseDTO userResponse = volunteerService.createVolunteer(accountRequest.getEmail(),
                    accountRequest.getPassword(), accountRequest.getUsername());

            logger.info("Volunteer created successfully: {}", userResponse.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (Exception e) {
            logger.error("Error creating volunteer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/response/events/{eventId}")
    public ResponseEntity<EventResponseDTO> responseToEvent(@PathVariable Long eventId,
                                                            HttpServletRequest request) {
        User currentUser = getUserFromSession(request);
        logger.info("User {} responding to event ID: {}", currentUser.getUsername(), eventId);

        try {
            EventResponseDTO eventResponse = volunteerService.responseToEvent(eventId, currentUser.getId());
            logger.info("Volunteer response to event ID {}: {}", eventId, eventResponse);
            return ResponseEntity.ok(eventResponse);
        } catch (Exception e) {
            logger.error("Error responding to event ID {}: {}", eventId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
