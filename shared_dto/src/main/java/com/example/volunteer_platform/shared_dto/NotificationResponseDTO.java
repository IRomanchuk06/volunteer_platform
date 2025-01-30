package com.example.volunteer_platform.shared_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {
    private Long id;
    private String senderEmail;
    private String recipientEmail;
    private String message;
    private String type;
    private LocalDateTime createdAt;
    private boolean isRead;
}
