package com.example.volunteer_platform.server.exceptions;

public class EventVolunteerLimitException extends RuntimeException {
    public EventVolunteerLimitException(String message) {
        super(message);
    }
}
