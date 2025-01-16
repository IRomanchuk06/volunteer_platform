package com.example.volunteer_platform.service;

import com.example.volunteer_platform.repository.UserRepository;
import com.example.volunteer_platform.utils.InputUtils;
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
        return InputUtils.isValidEmail(email);
    }

    public boolean isAvailableEmail(String email) {
        return !userRepository.existsUserByEmail(email);
    }
}
