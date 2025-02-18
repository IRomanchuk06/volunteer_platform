package com.example.volunteer_platform.server.exceptions;

public class EventNotExistsException extends RuntimeException {
    public EventNotExistsException(String message) {
        super(message);
    }
}
