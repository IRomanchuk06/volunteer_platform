package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exceptions.InvalidPasswordException;
import com.example.volunteer_platform.server.exceptions.UserNotFoundException;
import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.repository.UserRepository;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    public UserResponseDTO updateUser(String email, String newUsername, String oldPassword, String newPassword) {
        User user = getUserByEmail(email);
        if (!user.getPassword().equals(oldPassword)) {
            throw new InvalidPasswordException("You have entered an incorrect password");
        }
        user.setUsername(newUsername);
        user.setPassword(newPassword);
        userRepository.save(user);
        return userMapper.toUserResponseDTO(user);
    }

    public void deleteUser(String email) {
        User user = getUserByEmail(email);
        userRepository.delete(user);
    }

    public MessageResponseDTO sendMessage(String messageText, String senderEmail, String recipientEmail) {
        User sender = getUserByEmail(senderEmail);
        User recipient = getUserByEmail(recipientEmail);
        return messageService.sendMessage(messageText, sender, recipient);
    }
}

