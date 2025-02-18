package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.UserService;
import com.example.volunteer_platform.shared_dto.*;
import com.example.volunteer_platform.server.logging.AppLogger;
import org.slf4j.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.of(userService.getUserByEmail(email));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        logger.info("Incoming request to /users/username/{}", username);
        return ResponseEntity.of(userService.getUserByUsername(username));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UpdateUserDTO updateRequest) {
        logger.info("Incoming request to /users/update with data: {}", updateRequest);
        return ResponseEntity.ok(userService.updateUser(updateRequest.getEmail(),
                updateRequest.getUsername(), updateRequest.getOldPassword(), updateRequest.getNewPassword()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestParam String email) {
        logger.info("Incoming request to /users/delete with email: {}", email);
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/messages/")
    public ResponseEntity<MessageResponseDTO> sendMessage(@Valid @RequestBody MessageRegistrationDTO messageRequest,
                                                          HttpServletRequest request) {
        logger.info("Incoming request to /users/messages with data: {}", messageRequest);
        User currentUser = getUserFromSession(request);
        return ResponseEntity.ok(userService.sendMessage(messageRequest.getMessage(),
                messageRequest.getRecipientEmail(), currentUser.getEmail()));
    }
}

