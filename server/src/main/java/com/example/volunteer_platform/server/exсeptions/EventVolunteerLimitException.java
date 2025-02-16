package com.example.volunteer_platform.server.exсeptions;

public class EventVolunteerLimitException extends RuntimeException {
    public EventVolunteerLimitException(String message) {
        super(message);
    }
}
