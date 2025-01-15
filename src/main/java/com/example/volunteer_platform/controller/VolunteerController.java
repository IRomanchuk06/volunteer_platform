package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.model.Volunteer;
import com.example.volunteer_platform.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController extends UserController<Volunteer> {

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        super(volunteerService);
    }

    @PostMapping("/create/volunteer")
    public ResponseEntity<String> createVolunteer(@RequestParam String email,
                                                  @RequestParam String password,
                                                  @RequestParam String username) {
        try {
            VolunteerService volunteerService = (VolunteerService) service;
            volunteerService.createUserInstance(email, password, username);
            return ResponseEntity.status(HttpStatus.CREATED).body("Account successfully created for " + username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
