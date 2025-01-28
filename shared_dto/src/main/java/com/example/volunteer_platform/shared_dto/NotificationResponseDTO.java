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
    private UserResponseDTO sender;
    private UserResponseDTO recipient;
    private String message;
    private String type;
    private LocalDateTime createdAt;
    private boolean isRead;
}
