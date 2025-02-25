package com.example.volunteer_platform.server.exceptions;

public class UserNotFoundInSessionException extends RuntimeException {
    public UserNotFoundInSessionException(String message) {
        super(message);
    }
}
