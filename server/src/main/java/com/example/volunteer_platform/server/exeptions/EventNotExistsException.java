package com.example.volunteer_platform.server.exeptions;

public class EventNotExistsException extends RuntimeException {
    public EventNotExistsException(String message) {
        super(message);
    }
}
