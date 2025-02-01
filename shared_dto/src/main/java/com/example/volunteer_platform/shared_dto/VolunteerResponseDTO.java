package com.example.volunteer_platform.shared_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerResponseDTO {
    private Long id;
    private Long volunteerId;
    private String volunteerName;
    private String eventName;
    private String status;
    private LocalDateTime createdAt;
    private String message;
}