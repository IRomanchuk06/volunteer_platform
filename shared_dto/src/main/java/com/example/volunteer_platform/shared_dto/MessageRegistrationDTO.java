package com.example.volunteer_platform.shared_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRegistrationDTO {
    private String message;
    private String recipientEmail;
}
