package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.server.exeptions.InvalidEmailException;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.repository.UserRepository;
import com.example.volunteer_platform.shared_utils.VerificationUtils;
import org.springframework.stereotype.Service;

@Service("volunteerService")
public class VolunteerService extends UserService<Volunteer> {

    public VolunteerService(UserRepository repository) {
        super(repository);
    }

    @Override
    public Volunteer createUserInstance(String email, String password, String username) {
        if (repository.findUserByEmail(email) != null) {
            throw new EmailAlreadyExistsException(email + " this email is already registered.");
        }

        if (!VerificationUtils.isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email format.");
        }

        Volunteer volunteer = Volunteer.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();

        repository.save(volunteer);

        return volunteer;
    }
}
