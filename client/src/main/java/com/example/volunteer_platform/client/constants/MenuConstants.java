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

    public static final String ENTER_PASSWORD_PROMPT = "Enter password: ";
    public static final String ENTER_USERNAME_PROMPT = "Enter username: ";

    public static final String LOGIN_SUCCESS = "Login successful!";
    public static final String LOGIN_FAILED = "Login failed: ";
    public static final String LOGIN_ERROR = "An error occurred during login: ";

    public static final String SELECT_ACCOUNT_TYPE = "Select account type:";
    public static final String ACCOUNT_TYPE_OPTIONS =
            "1. Volunteer\n" +
                    "2. Customer";
    public static final String INVALID_ACCOUNT_CHOICE = "Invalid choice. Returning to the main menu.";

    public static final String EXIT_OPERATION_MESSAGE = "Exiting the operation.";

    public static final String ACCOUNT_CREATION_SUCCESS = "Account created successfully!";
    public static final String ACCOUNT_CREATION_FAILED = "Account creation failed. Status: ";
    public static final String ACCOUNT_CREATION_ERROR = "Error during account creation: ";

    public static final String VOLUNTEER = "VOLUNTEER";
    public static final String CUSTOMER = "CUSTOMER";
    public static final String INVALID_ACCOUNT_TYPE = "Invalid account type: ";

    public static final String RESPONSE_BODY = "Response body: ";
    public static final String NULL_RESPONSE_BODY = "Response body is null";

    public static final String FAILED_GET_COOKIES = "Failed to retrieve a cookie from the response.";
}
