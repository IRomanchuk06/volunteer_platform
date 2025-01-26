package com.example.volunteer_platform.shared_dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private String email;
    private String username;
    private String newPassword;
    private String oldPassword;
}
