package com.example.volunteer_platform.server.unit.controller;

import com.example.volunteer_platform.server.controller.advice.GlobalExceptionHandler;
import com.example.volunteer_platform.server.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTests {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleAuthenticationException() {
        AuthenticationException exception = new AuthenticationException("Authentication failed");
        ResponseEntity<String> response = globalExceptionHandler.handleAuthenticationException(exception);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Authentication failed", response.getBody());
    }

    @Test
    void handleEmailAlreadyExistsException() {
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException("Email already exists");
        ResponseEntity<String> response = globalExceptionHandler.handleEmailAlreadyExistsException(exception);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());
    }

    @Test
    void handleEventAlreadyExistsException() {
        EventAlreadyExistsException exception = new EventAlreadyExistsException("Event already exists");
        ResponseEntity<String> response = globalExceptionHandler.handleEventAlreadyExistsException(exception);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Event already exists", response.getBody());
    }

    @Test
    void handleInvalidPasswordException() {
        InvalidPasswordException exception = new InvalidPasswordException("Invalid password");
        ResponseEntity<String> response = globalExceptionHandler.handleInvalidPasswordException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid password", response.getBody());
    }

    @Test
    void handleSessionNotFoundException() {
        SessionNotFoundException exception = new SessionNotFoundException("Session not found");
        ResponseEntity<String> response = globalExceptionHandler.handleSessionNotFoundException(exception);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Session not found", response.getBody());
    }

    @Test
    void handleUserNotFoundInSessionException() {
        UserNotFoundInSessionException exception = new UserNotFoundInSessionException("User not found in session");
        ResponseEntity<String> response = globalExceptionHandler.handleUserNotFoundInSessionException(exception);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not found in session", response.getBody());
    }

    @Test
    void handleEventNotExistsException() {
        EventNotExistsException exception = new EventNotExistsException("Event does not exist");
        ResponseEntity<String> response = globalExceptionHandler.handleEventNotExistsException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Event does not exist", response.getBody());
    }

    @Test
    void handleEventVolunteerLimitException() {
        EventVolunteerLimitException exception = new EventVolunteerLimitException("Volunteer limit reached");
        ResponseEntity<String> response = globalExceptionHandler.handleEventVolunteerLimitException(exception);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Volunteer limit reached", response.getBody());
    }

    @Test
    void handleVolunteerAlreadyParticipatingException() {
        VolunteerAlreadyParticipatingException exception = new VolunteerAlreadyParticipatingException("Already participating");
        ResponseEntity<String> response = globalExceptionHandler.handleVolunteerAlreadyParticipatingException(exception);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Already participating", response.getBody());
    }

    @Test
    void handleAccessDeniedException() {
        AccessDeniedException exception = new AccessDeniedException("Access denied");
        ResponseEntity<String> response = globalExceptionHandler.handleAccessDeniedException(exception);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access denied", response.getBody());
    }

    @Test
    void handleEventValidationException() {
        EventValidationException exception = new EventValidationException("Event validation failed");
        ResponseEntity<String> response = globalExceptionHandler.handleEventValidationException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Event validation failed", response.getBody());
    }

    @Test
    void handleVolunteerParticipationException() {
        VolunteerParticipationException exception = new VolunteerParticipationException("Participation error");
        ResponseEntity<String> response = globalExceptionHandler.handleVolunteerParticipationException(exception);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Participation error", response.getBody());
    }

    @Test
    void handleInvalidEmailException() {
        InvalidEmailException exception = new InvalidEmailException("Invalid email");
        ResponseEntity<String> response = globalExceptionHandler.handleInvalidEmailException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email", response.getBody());
    }

    @Test
    void handleUserNotExistsException() {
        UserNotExistsException exception = new UserNotExistsException("User does not exist");
        ResponseEntity<String> response = globalExceptionHandler.handleUserNotExistsException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User does not exist", response.getBody());
    }

    @Test
    void handleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User not found");
        ResponseEntity<String> response = globalExceptionHandler.handleUserNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void handleGenericException() {
        Exception exception = new Exception("Unexpected error");
        ResponseEntity<String> response = globalExceptionHandler.handleGenericException(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred: Unexpected error", response.getBody());
    }
}