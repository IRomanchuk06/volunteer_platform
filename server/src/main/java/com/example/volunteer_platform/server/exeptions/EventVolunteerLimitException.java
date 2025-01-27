package com.example.volunteer_platform.server.exeptions;

public class EventVolunteerLimitException extends RuntimeException {
    public EventVolunteerLimitException(String message) {
        super(message);
    }
}
