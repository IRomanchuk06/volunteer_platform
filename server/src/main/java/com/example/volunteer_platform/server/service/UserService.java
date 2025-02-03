package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exeptions.InvalidPasswordException;
import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.repository.UserRepository;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userService")
public class UserService {

    protected final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageService messageService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, MessageService messageService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.messageService = messageService;
    }

    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findUserByEmail(email));
    }

    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findUserByUsername(username));
    }

    public UserResponseDTO updateUser(String email, String newUsername, String oldPassword, String newPassword) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            if (!user.getPassword().equals(oldPassword)) {
                throw new InvalidPasswordException("You have entered an incorrect password");
            }
            user.setUsername(newUsername);
            user.setPassword(newPassword);
            userRepository.save(user);

            return userMapper.toUserResponseDTO(user);
        }
        return null;
    }

    public boolean deleteUser(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public MessageResponseDTO sendMessage(String messageText, String senderEmail, String recipientEmail) {
        User sender = userRepository.findUserByEmail(senderEmail);
        User recipient = userRepository.findUserByEmail(recipientEmail);
        return messageService.sendMessage(messageText, sender, recipient);
    }
}
