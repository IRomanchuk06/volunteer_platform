package com.example.volunteer_platform.shared_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventParticipantResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
}
