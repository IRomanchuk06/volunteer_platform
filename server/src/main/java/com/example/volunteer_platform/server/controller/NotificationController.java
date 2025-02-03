package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.exeptions.AccessDeniedException;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.NotificationService;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerEventResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/received/messages")
    public ResponseEntity<List<MessageResponseDTO>> getReceivedMessages(HttpServletRequest request) {
        User user = getUserFromSession(request);
        return ResponseEntity.ok(notificationService.getReceivedMessages(user));
    }

    @GetMapping("/received/responses")
    public ResponseEntity<List<VolunteerEventResponseDTO>> getVolunteerResponses(HttpServletRequest request) {
        User user = getUserFromSession(request);

        if (!user.getRole().equals(User.UserRole.CUSTOMER)) {
            throw new AccessDeniedException("Event feedback is only available to the customer.");
        }

        return ResponseEntity.ok(notificationService.getVolunteerResponses(user));
    }
}
