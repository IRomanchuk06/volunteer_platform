package com.example.volunteer_platform.server.exeptions;

public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(String message) {
        super(message);
    }
}
