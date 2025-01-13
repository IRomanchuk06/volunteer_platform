package com.example.volunteer_platform.controller;

import com.example.volunteer_platform.exeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.exeptions.InvalidEmailException;
import com.example.volunteer_platform.model.Volunteer;
import com.example.volunteer_platform.service.CustomerService;
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

    @PostMapping("/create")
    public void createVolunteer(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String username) {
        try {
            VolunteerService volunteerService = (VolunteerService) service;
            volunteerService.createVolunteer(email, password, username);
        } catch (EmailAlreadyExistsException | InvalidEmailException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }
}
