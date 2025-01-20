package com.example.volunteer_platform.client.constants;

public class ApiEndpoints {
    public static final String BASE_URL = "http://localhost:8080";

    public static final String EMAIL_VALIDATION_URL = "/verification/emails/valid?email=";
    public static final String EMAIL_CHECK_AVAILABILITY_URL = "/verification/emails/available?email=";

    public static final String LOGIN_URL = "/auth/login";
    public static final String LOGOUT_URL = "/auth/logout";

    public static final String VOLUNTEER_URL = "/volunteers/";
    public static final String CUSTOMER_URL = "/customers/";
}
