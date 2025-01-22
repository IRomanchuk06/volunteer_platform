package com.example.volunteer_platform.client.request_builder;

import com.example.volunteer_platform.client.dto.LoginRequest;
import com.example.volunteer_platform.client.dto.RegistrationRequest;
import org.springframework.http.HttpEntity;

import static com.example.volunteer_platform.client.utils.RequestBuilderUtils.createJsonRequestEntity;
import static com.example.volunteer_platform.client.utils.RequestBuilderUtils.validateNonNull;

public class AccountRequestBuilder {

    public static HttpEntity<String> createRegistrationRequest(String email, String password, String username) {
        validateNonNull(email, password, username);

        RegistrationRequest registrationRequest = new RegistrationRequest(email, password, username);
        return createJsonRequestEntity(registrationRequest);
    }

    public static HttpEntity<String> createLoginRequest(String email, String password) {
        validateNonNull(email, password);

        LoginRequest loginRequest = new LoginRequest(email, password);
        return createJsonRequestEntity(loginRequest);
    }
}
