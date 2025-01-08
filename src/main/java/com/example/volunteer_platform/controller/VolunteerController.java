package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.model.Volunteer;
import com.example.volunteer_platform.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController extends UserController<Volunteer> {

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        super(volunteerService);
    }

    @Override
    protected Volunteer createUserInstance(String email, String password, String username) {
        return new Volunteer(email, password, username);
    }
}
