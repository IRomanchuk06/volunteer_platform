package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.dto.RegistrationRequest;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController extends UserController<Volunteer> {

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        super(volunteerService);
    }

    @PostMapping("/")
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody RegistrationRequest accountRequest) {
        VolunteerService volunteerService = (VolunteerService) service;
        Volunteer volunteer = volunteerService.createUserInstance(
                accountRequest.getEmail(),
                accountRequest.getPassword(),
                accountRequest.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(volunteer);
    }
}

