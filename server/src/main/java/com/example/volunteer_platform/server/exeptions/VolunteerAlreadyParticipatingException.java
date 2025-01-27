package com.example.volunteer_platform.server.exeptions;

public class VolunteerAlreadyParticipatingException extends RuntimeException {
    public VolunteerAlreadyParticipatingException(String message) {
        super(message);
    }
}
