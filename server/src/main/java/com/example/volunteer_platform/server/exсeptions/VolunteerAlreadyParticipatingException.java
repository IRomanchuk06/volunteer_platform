package com.example.volunteer_platform.server.exсeptions;

public class VolunteerAlreadyParticipatingException extends RuntimeException {
    public VolunteerAlreadyParticipatingException(String message) {
        super(message);
    }
}
