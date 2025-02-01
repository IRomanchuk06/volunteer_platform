package com.example.volunteer_platform.client.constants;

import java.time.format.DateTimeFormatter;

public class UtilsConstants {
    public static final String ENTER_DATE_PROMPT = "Enter event date YYYY-MM-DD (exit to abort): ";
    public static final String ENTER_START_TIME_PROMPT = "Enter event start HH:MM (exit to abort): ";
    public static final String ENTER_END_TIME_PROMPT = "Enter event end time HH:MM (exit to abort): ";
    public static final String INVALID_DATE_FORMAT = "Invalid date format. Please use YYYY-MM-DD.";
    public static final String INVALID_TIME_FORMAT = "Invalid time format. Please use HH:mm.";

    public static final String ENTER_EMAIL_PROMPT = "Enter email (exit to abort): ";
    public static final String EMAIL_VALIDATING_ERROR = "Error validating email. Please try again.";
    public static final String EMAIL_AVAILABILITY_ERROR = "Error checking email availability. Please try again.";
    public static final String INVALID_EMAIL_FORMAT = "Invalid email format. Please enter a valid email address.";
    public static final String EMAIL_ALREADY_REGISTERED = "Email is already registered. Please enter a different email address.";
    public static final String CHOOSE_OPTION = "Choose an option: ";
    public static final String NO_INPUT = "No input entered.";
    public static final String READING_ERROR = "Error reading input. Exiting...";
    public static final String INVALID_CHOICE = "Invalid choice. Please try again.";
    public static final String INVALID_ACCOUNT_TYPE = "Invalid account type: ";
    public static final String ENTER_POSITIVE_NUMBER_PROMPT = "Enter a number of required volunteers: ";
    public static final String INVALID_POSITIVE_NUMBER = "Error: the number must be positive. Please try again.";
    public static final String INVALID_NUMBER_FORMAT = "Error: invalid input. Please enter a valid number.";
    public static final String ENTER_EVENT_ID_PROMPT = "Enter the event ID: ";

    public static final String EVENT_DETAILS_HEADER = "Event Details:\n";
    public static final String NAME_LABEL = "  Name: ";
    public static final String DESCRIPTION_LABEL = "  Description: ";
    public static final String LOCATION_LABEL = "  Location: ";
    public static final String DATE_LABEL = "  Date: ";
    public static final String TIME_LABEL = "  Time: ";
    public static final String CUSTOMER_LABEL = "  Customer: ";
    public static final String NOT_AVAILABLE = "N/A";
    public static final String EVENT_NOT_AVAILABLE = "Event not available";
    public static final String NEW_LINE = "\n";
    public static final String ID_LABEL = "  Id: ";
    public static final String EXIT = "exit";
    public static final String EVENT_VOLUNTEER_TEAM = "  Volunteer team: ";

    public static final String NULL_PARAMETER_ERROR = "All parameters must be non-null";

    public static final String SESSION_ID_COOKIE_NAME = "JSESSIONID=";
    public static final String SET_COOKIE_HEADER = "Set-Cookie";
    public static final String COOKIE_SEPARATOR = ";";
    public static final String COOKIE_VALUE_SEPARATOR = "=";
    public static final String DASH = " - ";
    public static final String BACKSLASH = "\\";

    public static final String NOTIFICATION_NOT_AVAILABLE = "Notification not available";
    public static final String NOTIFICATION_HEADER = "=== Message Details ===\n";
    public static final String SENDER_LABEL = "From: ";
    public static final String RECIPIENT_LABEL = "To: ";
    public static final String TYPE_LABEL = "Type: ";
    public static final String STATUS_LABEL = "Read: ";
    public static final String MESSAGE_HEADER = "\nMessage:\n";

    public static final String VOLUNTEER_RESPONSE_HEADER = "----- Volunteer Response Details -----\n";
    public static final String VOLUNTEER_LABEL = "Volunteer: ";
    public static final String EVENT_LABEL = "Event: ";
    public static final String RESPONSE_TIME_LABEL = "Response Time: ";
    public static final String VOLUNTEER_RESPONSE_NOT_AVAILABLE = "Volunteer response not available.";
}
