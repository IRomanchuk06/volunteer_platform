package com.example.volunteer_platform.client.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.example.volunteer_platform.client.constants.UtilsConstants.*;

public class RequestBuilderUtils {
    public static String convertToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(JSON_CONVERSION_ERROR, e);
        }
    }

    public static HttpEntity<String> createJsonRequestEntity(Object request) {
        String json = convertToJson(request);
        System.out.println("Generated JSON: " + json);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(json, headers);
    }

    public static void validateNonNull(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException(NULL_PARAMETER_ERROR);
            }
        }
    }
}
