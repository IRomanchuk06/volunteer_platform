package com.example.volunteer_platform.exeptions;

public class EventAlreadyExistsException extends RuntimeException{
    public EventAlreadyExistsException(String message) {
        super(message);
    }
}
