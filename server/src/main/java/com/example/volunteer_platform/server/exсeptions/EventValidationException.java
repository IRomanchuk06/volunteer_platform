package com.example.volunteer_platform.server.exсeptions;

public class EventValidationException extends RuntimeException {
    public EventValidationException(String message) {
        super(message);
    }
}
