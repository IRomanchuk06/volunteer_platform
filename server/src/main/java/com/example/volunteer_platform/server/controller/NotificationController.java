package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.exeptions.AccessDeniedException;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.NotificationService;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerEventResponseDTO;
import com.example.volunteer_platform.server.logging.AppLogger;
import org.slf4j.Logger;
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
    private static final Logger logger = AppLogger.SERVER_LOGGER;

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/received/messages")
    public ResponseEntity<List<MessageResponseDTO>> getReceivedMessages(HttpServletRequest request) {
        logger.info("Incoming request to /notifications/received/messages");

        User user = getUserFromSession(request);
        List<MessageResponseDTO> messages = notificationService.getReceivedMessages(user);

        logger.info("Responding with status 200 and payload: {}", messages);

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/received/responses")
    public ResponseEntity<List<VolunteerEventResponseDTO>> getVolunteerResponses(HttpServletRequest request) {
        logger.info("Incoming request to /notifications/received/responses");

        User user = getUserFromSession(request);
        logger.info(String.valueOf(user.getRole()));

        if (!user.getRole().equals(User.UserRole.CUSTOMER)) {
            logger.warn("Access denied for user with role: {}", user.getRole());
            throw new AccessDeniedException("Event feedback is only available to the customer.");
        }

        List<VolunteerEventResponseDTO> responses = notificationService.getVolunteerResponses(user);

        logger.info("Responding with status 200 and payload: {}", responses);

        return ResponseEntity.ok(responses);
    }
}
