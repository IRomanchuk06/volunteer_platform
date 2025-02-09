package com.example.volunteer_platform.server.controller.advice;

import com.example.volunteer_platform.server.exeptions.*;
import com.example.volunteer_platform.server.logging.AppLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        AppLogger.SERVER_LOGGER.error("AuthenticationException occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        AppLogger.SERVER_LOGGER.error("EmailAlreadyExistsException occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(EventAlreadyExistsException.class)
    public ResponseEntity<String> handleEventAlreadyExistsException(EventAlreadyExistsException e) {
        AppLogger.SERVER_LOGGER.error("EventAlreadyExistsException occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleInvalidEmailException(InvalidEmailException e) {
        AppLogger.SERVER_LOGGER.error("InvalidEmailException occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException e) {
        AppLogger.SERVER_LOGGER.error("InvalidPasswordException occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<String> handleSessionNotFoundException(SessionNotFoundException e) {
        AppLogger.SERVER_LOGGER.error("SessionNotFoundException occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundInSessionException.class)
    public ResponseEntity<String> handleUserNotFoundInSessionException(UserNotFoundInSessionException e) {
        AppLogger.SERVER_LOGGER.error("UserNotFoundInSessionException occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(EventNotExistsException.class)
    public ResponseEntity<String> handleEventNotExistsException(EventNotExistsException e) {
        AppLogger.SERVER_LOGGER.error("EventNotExistsException occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(EventVolunteerLimitException.class)
    public ResponseEntity<String> handleEventVolunteerLimitException(EventVolunteerLimitException e) {
        AppLogger.SERVER_LOGGER.error("EventVolunteerLimitException occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(VolunteerAlreadyParticipatingException.class)
    public ResponseEntity<String> handleVolunteerAlreadyParticipatingException(
            VolunteerAlreadyParticipatingException e) {
        AppLogger.SERVER_LOGGER.error("VolunteerAlreadyParticipatingException occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        AppLogger.SERVER_LOGGER.error("An unexpected error occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "An unexpected error occurred: " + e.getMessage());
    }
}