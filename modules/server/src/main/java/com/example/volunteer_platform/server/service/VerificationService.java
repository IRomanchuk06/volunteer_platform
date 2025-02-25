package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.repository.UserRepository;
import com.example.volunteer_platform.shared_utils.VerificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    private final UserRepository userRepository;

    @Autowired
    VerificationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidEmail(String email) {
        return VerificationUtils.isValidEmail(email);
    }

    public boolean isAvailableEmail(String email) {
        return !userRepository.existsUserByEmail(email);
    }
}
