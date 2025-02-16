package com.example.volunteer_platform.server.exсeptions;

public class UserNotFoundInSessionException extends RuntimeException {
    public UserNotFoundInSessionException(String message) {
        super(message);
    }
}
