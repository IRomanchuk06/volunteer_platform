package com.example.volunteer_platform.shared_dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRegistrationDTO {

    @NotBlank(message = "Message cannot be blank")
    @Size(max = 1000, message = "Message cannot exceed 1000 characters")
    private String message;

    @NotBlank(message = "Recipient email cannot be blank")
    @Email(message = "Recipient email must be a valid email address")
    private String recipientEmail;
}
