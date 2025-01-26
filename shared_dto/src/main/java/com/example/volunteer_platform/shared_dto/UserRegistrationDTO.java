package com.example.volunteer_platform.shared_dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
    private String username;
    private String password;
    private String email;
}
