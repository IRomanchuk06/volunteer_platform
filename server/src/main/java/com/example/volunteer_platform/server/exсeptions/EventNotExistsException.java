package com.example.volunteer_platform.server.exсeptions;

public class EventNotExistsException extends RuntimeException {
    public EventNotExistsException(String message) {
        super(message);
    }
}
