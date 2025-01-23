package com.example.volunteer_platform.server.exeptions;

public class UserNotFoundInSessionException extends RuntimeException {
    public UserNotFoundInSessionException(String message) {
        super(message);
    }
}
