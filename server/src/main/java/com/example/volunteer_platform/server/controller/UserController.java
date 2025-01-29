package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.UserService;
import com.example.volunteer_platform.shared_dto.MessageRegistrationDTO;
import com.example.volunteer_platform.shared_dto.NotificationResponseDTO;
import com.example.volunteer_platform.shared_dto.UpdateUserDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
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

    protected final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username: " + username));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UpdateUserDTO updateRequest) {
        UserResponseDTO updatedUserResponse = userService.updateUser(updateRequest.getEmail(),
                updateRequest.getUsername(), updateRequest.getOldPassword(), updateRequest.getNewPassword());

        return ResponseEntity.ok(updatedUserResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        boolean isDeleted = userService.deleteUser(email);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }
    }

    @PostMapping("/message/")
    public ResponseEntity<NotificationResponseDTO> sendMessage(@RequestBody MessageRegistrationDTO messageRequest,
                                                               HttpServletRequest request) {
        System.out.println(messageRequest);

        User currentUser = getUserFromSession(request);
        NotificationResponseDTO responseDTO = userService.sendMessage(messageRequest.getMessage(),
                messageRequest.getRecipientEmail(), currentUser.getEmail());
        return ResponseEntity.ok(responseDTO);
    }
}
