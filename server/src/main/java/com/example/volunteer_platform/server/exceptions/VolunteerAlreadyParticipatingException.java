package com.example.volunteer_platform.server.exceptions;

public class VolunteerAlreadyParticipatingException extends RuntimeException {
    public VolunteerAlreadyParticipatingException(String message) {
        super(message);
    }
}
