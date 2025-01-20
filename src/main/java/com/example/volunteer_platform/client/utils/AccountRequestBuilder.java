package com.example.volunteer_platform.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;
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

    private static void validateNonNull(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("All parameters must be non-null");
            }
        }
    }

    public static HttpEntity<String> createRegistrationRequest(String email, String password, String username) {
        validateNonNull(email, password, username);

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("email", email);
        requestParams.put("password", password);
        requestParams.put("username", username);

        return createJsonRequestEntity(requestParams);
    }

    public static HttpEntity<String> createLoginRequest(String email, String password) {
        validateNonNull(email, password);

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("email", email);
        loginParams.put("password", password);

        return createJsonRequestEntity(loginParams);
    }

    public static HttpEntity<String> createAddEventRequest(String name, String description, String location,
                                                           LocalDate date, LocalTime startTime, LocalTime endTime) {
        validateNonNull(name, description, location, date);

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("name", name);
        requestParams.put("description", description);
        requestParams.put("location", location);
        requestParams.put("date", date.toString());
        requestParams.put("startTime", startTime.toString());
        requestParams.put("endTime", endTime.toString());

        return createJsonRequestEntity(requestParams);
    }
}
