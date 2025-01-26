package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.service.VolunteerService;
import com.example.volunteer_platform.shared_dto.UserRegistrationDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import jakarta.validation.Valid;
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

    private final UserMapper userMapper;

    @Autowired
    public VolunteerController(VolunteerService volunteerService, UserMapper userMapper) {
        super(volunteerService, userMapper);
        this.userMapper = userMapper;
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> createVolunteer(@Valid @RequestBody UserRegistrationDTO accountRequest) {
        VolunteerService volunteerService = (VolunteerService) service;
        Volunteer volunteer = volunteerService.createUserInstance(
                accountRequest.getEmail(),
                accountRequest.getPassword(),
                accountRequest.getUsername());

        UserResponseDTO response = userMapper.toUserResponseDTO(volunteer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

