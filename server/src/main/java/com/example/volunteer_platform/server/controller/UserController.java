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
        User user = userService.getUserByEmail(email);
        ResponseEntity<User> response = ResponseEntity.ok(user);
        logger.info("Server response: {}", response);
        return response;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        logger.info("Incoming request to /users/username/{}", username);
        User user = userService.getUserByUsername(username);
        ResponseEntity<User> response = ResponseEntity.ok(user);
        logger.info("Server response: {}", response);
        return response;
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UpdateUserDTO updateRequest) {
        logger.info("Incoming request to /users/update with data: {}", updateRequest);
        UserResponseDTO response = userService.updateUser(updateRequest.getEmail(),
                updateRequest.getUsername(), updateRequest.getOldPassword(), updateRequest.getNewPassword());
        logger.info("Server response: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
        logger.info("Incoming request to /users/delete with email: {}", email);
        boolean response = userService.deleteUser(email);

        if (response) {
            logger.info("User deleted successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");
        } else {
            logger.info("User deletion unsuccessful");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with email: " + email);
        }
    }

    @PostMapping("/messages/")
    public ResponseEntity<MessageResponseDTO> sendMessage(@Valid @RequestBody MessageRegistrationDTO messageRequest,
                                                          HttpServletRequest request) {
        logger.info("Incoming request to /users/messages with data: {}", messageRequest);
        User currentUser = getUserFromSession(request);
        ResponseEntity<MessageResponseDTO> response = ResponseEntity.ok(userService.sendMessage(messageRequest.getMessage(),
                messageRequest.getRecipientEmail(), currentUser.getEmail()));
        logger.info("Server response: {}", response);
        return response;
    }
}
