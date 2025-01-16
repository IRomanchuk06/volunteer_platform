package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.model.Volunteer;
import com.example.volunteer_platform.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController extends UserController<Volunteer> {

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        super(volunteerService);
    }

    @PostMapping("/")
    public ResponseEntity<Volunteer> createVolunteer(@RequestParam String email,
                                                  @RequestParam String password,
                                                  @RequestParam String username) {
        VolunteerService volunteerService = (VolunteerService) service;
        Volunteer volunteer = volunteerService.createUserInstance(email, password, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(volunteer);
    }
}

