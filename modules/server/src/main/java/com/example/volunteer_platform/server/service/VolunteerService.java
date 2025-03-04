package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exceptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.server.exceptions.InvalidEmailException;
import com.example.volunteer_platform.server.exceptions.UserNotExistsException;
import com.example.volunteer_platform.server.exceptions.VolunteerAlreadyParticipatingException;
import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.repository.UserRepository;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import com.example.volunteer_platform.shared_utils.VerificationUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("volunteerService")
public class VolunteerService extends UserService {

    private final EventService eventService;
    private final UserMapper userMapper;

    public VolunteerService(UserRepository repository, EventService eventService, UserMapper userMapper,
                            MessageService messageService) {
        super(repository, userMapper, messageService);
        this.eventService = eventService;
        this.userMapper = userMapper;
    }

    public UserResponseDTO createVolunteer(String email, String password, String username) {
        if (userRepository.findUserByEmail(email) != null) {
            throw new EmailAlreadyExistsException(email + " this email is already registered.");
        }

        if (!VerificationUtils.isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email format: " + email);
        }

        Volunteer volunteer = Volunteer.builder().email(email).password(password).username(username).role(
                User.UserRole.VOLUNTEER).build();

        userRepository.save(volunteer);

        return userMapper.toUserResponseDTO(volunteer);
    }

    public EventResponseDTO responseToEvent(Long eventId, Long userId) {
        boolean isParticipating = userRepository.existsByEventIdAndVolunteerId(eventId, userId);

        Optional<User> volunteer = userRepository.findById(userId);
        if(volunteer.isEmpty()) {
            throw new UserNotExistsException("Volunteer with this id is not found");
        }

        if (isParticipating) {
            throw new VolunteerAlreadyParticipatingException("Volunteer already participating");
        }

        return eventService.responseToEvent(eventId, (Volunteer) volunteer.get());
    }
}
