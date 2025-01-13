package com.example.volunteer_platform.service;

import com.example.volunteer_platform.exeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.exeptions.InvalidEmailException;
import com.example.volunteer_platform.model.Volunteer;
import com.example.volunteer_platform.repository.VolunteerRepository;
import com.example.volunteer_platform.utils.InputUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("volunteerService")
public class VolunteerService extends UserService<Volunteer> {

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        super(volunteerRepository);
    }

    @Override
    public Volunteer createUserInstance(String email, String password, String username) {
         Volunteer volunteer = Volunteer.builder()
                                        .email(email)
                                        .password(password)
                                        .username(username)
                                        .build();

         repository.save(volunteer);

         return volunteer;
    }

    public Volunteer createVolunteer(String email, String password, String username) {
        if(repository.findUserByEmail(email) != null) {
            if(!InputUtils.isValidEmail(email)) {
                throw new InvalidEmailException("Invalid email format.");
            }

            return createUserInstance(email, password, username);
        }
        throw new EmailAlreadyExistsException(email + " this email is already registered.");
    }
}
