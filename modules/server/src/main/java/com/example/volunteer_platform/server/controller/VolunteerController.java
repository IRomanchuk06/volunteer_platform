package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.VolunteerService;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.UserRegistrationDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.volunteer_platform.server.utils.SessionUtils.getUserFromSession;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController extends UserController {

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        super(volunteerService);
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> createVolunteer(@Valid @RequestBody UserRegistrationDTO accountRequest) {
        VolunteerService volunteerService = (VolunteerService) userService;

        serverLogger.info("Received request to create volunteer with email: {}", accountRequest.getEmail());

        UserResponseDTO userResponse = volunteerService.createVolunteer(
                accountRequest.getEmail(),
                accountRequest.getPassword(),
                accountRequest.getUsername()
        );

        serverLogger.info("Volunteer created successfully: {}", userResponse.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/response/events/{eventId}")
    public ResponseEntity<EventResponseDTO> responseToEvent(
            @PathVariable Long eventId,
            HttpServletRequest request
    ) {
        VolunteerService volunteerService = (VolunteerService) userService;

        User currentUser = getUserFromSession(request);
        serverLogger.info("User {} responding to event ID: {}", currentUser.getUsername(), eventId);

        EventResponseDTO eventResponse = volunteerService.responseToEvent(eventId, currentUser.getId());
        serverLogger.info("Volunteer response to event ID {}: {}", eventId, eventResponse);
        return ResponseEntity.ok(eventResponse);
    }
}