package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.exceptions.AccessDeniedException;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.NotificationService;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerEventResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.volunteer_platform.server.utils.SessionUtils.getUserFromSession;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private static final Logger serverLogger = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/received/messages")
    public ResponseEntity<List<MessageResponseDTO>> getReceivedMessages(HttpServletRequest request) {
        serverLogger.info("Incoming request to /notifications/received/messages");

        User user = getUserFromSession(request);
        List<MessageResponseDTO> messages = notificationService.getReceivedMessages(user);

        serverLogger.info("Responding with status 200 and payload: {}", messages);

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/received/responses")
    public ResponseEntity<List<VolunteerEventResponseDTO>> getVolunteerResponses(HttpServletRequest request) {
        serverLogger.info("Incoming request to /notifications/received/responses");

        User user = getUserFromSession(request);
        serverLogger.info(String.valueOf(user.getRole()));

        if (!user.getRole().equals(User.UserRole.CUSTOMER)) {
            serverLogger.warn("Access denied for user with role: {}", user.getRole());
            throw new AccessDeniedException("Event feedback is only available to the customer.");
        }

        List<VolunteerEventResponseDTO> responses = notificationService.getVolunteerResponses(user);

        serverLogger.info("Responding with status 200 and payload: {}", responses);

        return ResponseEntity.ok(responses);
    }
}
