package com.example.volunteer_platform.shared_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Size(min = 4, message = "New password must be at least 4 characters long")
    private String newPassword;

    @NotBlank(message = "Old password cannot be empty")
    private String oldPassword;
}
