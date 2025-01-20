package com.example.volunteer_platform.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;


public class AccountRequestBuilder {

    private static String convertToJson(Map<String, String> params) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(params);
        } catch (Exception e) {
            throw new RuntimeException("Error while converting parameters to JSON", e);
        }
    }

    private static HttpEntity<String> createJsonRequestEntity(Map<String, String> params) {
        String json = convertToJson(params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(json, headers);
    }

    public static HttpEntity<String> createRegistrationRequest(String email, String password, String username, String accountTypeUrl) {
        if (email == null || password == null || username == null || accountTypeUrl == null) {
            throw new IllegalArgumentException("All parameters must be non-null");
        }

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("email", email);
        requestParams.put("password", password);
        requestParams.put("username", username);

        return createJsonRequestEntity(requestParams);
    }

    public static HttpEntity<String> createLoginRequest(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Both email and password must be non-null");
        }

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("email", email);
        loginParams.put("password", password);

        return createJsonRequestEntity(loginParams);
    }
}
