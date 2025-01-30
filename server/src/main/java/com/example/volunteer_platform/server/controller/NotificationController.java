package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.exeptions.AccessDeniedException;
import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.NotificationService;
import com.example.volunteer_platform.shared_dto.NotificationResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.volunteer_platform.server.utils.SessionUtils.getUserFromSession;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/sent")
    public ResponseEntity<List<NotificationResponseDTO>> getSentNotifications(HttpServletRequest request) {
        User user = getUserFromSession(request);
        return ResponseEntity.ok(notificationService.getSentNotifications(user));
    }

    @GetMapping("/received")
    public ResponseEntity<List<NotificationResponseDTO>> getReceivedNotifications(HttpServletRequest request) {
        User user = getUserFromSession(request);
        return ResponseEntity.ok(notificationService.getReceivedNotifications(user));
    }

    @GetMapping("/received/messages")
    public ResponseEntity<List<NotificationResponseDTO>> getReceivedMessages(HttpServletRequest request) {
        User user = getUserFromSession(request);
        return ResponseEntity.ok(notificationService.getReceivedMessages(user));
    }

    @GetMapping("/responses")
    public ResponseEntity<List<NotificationResponseDTO>> getVolunteerResponses(HttpServletRequest request) {
        User user = getUserFromSession(request);

        if (!user.getRole().equals("VOLUNTEER")) {
            throw new AccessDeniedException("Event feedback is only available to the customer.");
        }

        return ResponseEntity.ok(notificationService.getVolunteerResponses(user));
    }
}
