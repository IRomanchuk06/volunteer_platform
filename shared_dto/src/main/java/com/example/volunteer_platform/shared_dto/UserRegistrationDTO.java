package com.example.volunteer_platform.shared_dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
    private String email;
    private String password;
    private String username;
}
