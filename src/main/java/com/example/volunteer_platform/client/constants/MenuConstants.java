package com.example.volunteer_platform.client.constants;

public class MenuConstants {

    public static final String MAIN_MENU_TITLE = "--- Volunteer Platform Console ---";
    public static final String MAIN_MENU_OPTIONS =
            "1. Create Account\n" +
                    "2. Login Account\n" +
                    "3. Exit";

    public static final String INVALID_CHOICE = "Invalid choice. Please try again.";
    public static final String EXIT_MESSAGE = "Exiting...";
    public static final String EXIT_ERROR = "Logout failed. Please try again.";

    public static final String ENTER_EMAIL_PROMPT = "Enter email (or type 'exit' to quit): ";
    public static final String ENTER_PASSWORD_PROMPT = "Enter password: ";
    public static final String ENTER_USERNAME_PROMPT = "Enter username: ";

    public static final String INVALID_EMAIL_FORMAT = "Invalid email format. Please enter a valid email address.";
    public static final String EMAIL_ALREADY_REGISTERED = "Email is already registered. Please enter a different email address.";
    public static final String LOGIN_SUCCESS = "Login successful!";
    public static final String LOGIN_FAILED = "Login failed: ";
    public static final String LOGIN_ERROR = "An error occurred during login: ";

    public static final String SELECT_ACCOUNT_TYPE = "Select account type:";
    public static final String ACCOUNT_TYPE_OPTIONS =
            "1. Volunteer\n" +
                    "2. Customer";
    public static final String INVALID_ACCOUNT_TYPE = "Invalid choice. Returning to the main menu.";

    public static final String CHOOSE_OPTION = "Choose an option: ";
    public static final String NO_INPUT = "No input entered.";
    public static final String READING_ERROR = "Error reading input. Exiting...";
    public static final String EXIT_OPERATION_MESSAGE = "Exiting the operation.";

    public static final String EMAIL_VALIDATING_ERROR = "Error validating email. Please try again.";
    public static final String EMAIL_AVAILABILITY_ERROR = "Error checking email availability. Please try again.";

    public static final String ACCOUNT_CREATION_SUCCESS = "Account created successfully!";
    public static final String ACCOUNT_CREATION_FAILED = "Account creation failed. Status: ";
    public static final String ACCOUNT_CREATION_ERROR = "Error during account creation: ";
}
