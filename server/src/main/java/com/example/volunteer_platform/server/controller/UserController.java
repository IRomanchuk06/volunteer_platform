package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.UserService;
import com.example.volunteer_platform.shared_dto.*;
import com.example.volunteer_platform.server.logging.AppLogger;
import org.slf4j.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.example.volunteer_platform.server.utils.SessionUtils.getUserFromSession;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = AppLogger.SERVER_LOGGER;

    protected final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        logger.info("Incoming request to /users/email/{}", email);

        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            logger.info("User found: {}", user.get());
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User not found with email: {}", email);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        logger.info("Incoming request to /users/username/{}", username);

        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            logger.info("User found: {}", user.get());
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User not found with username: {}", username);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username: " + username);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UpdateUserDTO updateRequest) {
        logger.info("Incoming request to /users/update with data: {}", updateRequest);

        UserResponseDTO updatedUserResponse = userService.updateUser(updateRequest.getEmail(),
                updateRequest.getUsername(), updateRequest.getOldPassword(), updateRequest.getNewPassword());

        logger.info("User updated successfully: {}", updatedUserResponse);
        return ResponseEntity.ok(updatedUserResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        logger.info("Incoming request to /users/delete with email: {}", email);

        boolean isDeleted = userService.deleteUser(email);
        if (isDeleted) {
            logger.info("User deleted successfully with email: {}", email);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            logger.warn("User not found with email: {}", email);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }
    }

    @PostMapping("/messages/")
    public ResponseEntity<MessageResponseDTO> sendMessage(@Valid @RequestBody MessageRegistrationDTO messageRequest,
                                                          HttpServletRequest request) {
        logger.info("Incoming request to /users/messages with data: {}", messageRequest);

        User currentUser = getUserFromSession(request);
        MessageResponseDTO responseDTO = userService.sendMessage(messageRequest.getMessage(),
                messageRequest.getRecipientEmail(), currentUser.getEmail());

        logger.info("Message sent successfully, response: {}", responseDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
