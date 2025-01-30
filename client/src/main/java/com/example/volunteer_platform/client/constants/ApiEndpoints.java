package com.example.volunteer_platform.client.constants;

public class ApiEndpoints {
    public static final String BASE_URL = "http://localhost:8080";

    public static final String EMAIL_VALIDATION_URL = "/verification/emails/valid?email=";
    public static final String EMAIL_CHECK_AVAILABILITY_URL = "/verification/emails/available?email=";

    public static final String LOGIN_URL = "/auth/login";
    public static final String LOGOUT_URL = "/auth/logout";

    public static final String CREATE_URL = "/";

    public static final String VOLUNTEERS_URL = "/volunteers";
    public static final String CUSTOMERS_URL = "/customers";
    public static final String EVENT_URL = "/events";
    public static final String USERS_URL = "/users";
    public static final String NOTIFICATIONS_URL = "/notifications";
    public static final String RECEIVED_URL = "/received";
    public static final String MESSAGES_URL = "/messages";

    public static final String RESPONSE_TO_EVENT_URL = "/response/events/";
}
