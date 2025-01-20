package com.example.volunteer_platform.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {
    private String email;
    private String username;
    private String oldPassword;
    private String newPassword;
}
