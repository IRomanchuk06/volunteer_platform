package com.example.volunteer_platform.service;

import com.example.volunteer_platform.model.Volunteer;
import com.example.volunteer_platform.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerService extends UserService<Volunteer> {

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        super(volunteerRepository);
    }

    public Volunteer createVolunteer(String email, String password, String username) {
        return createUserInstance(email, password, username);
    }

    @Override
    protected Volunteer createUserInstance(String email, String password, String username) {
        return new Volunteer(email, password, username);
    }
}
