package com.example.volunteer_platform.client.utils;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class CookieUtils {

    public static String getSessionIdFromResponse(ResponseEntity<?> response) {
        List<String> cookies = response.getHeaders().get("Set-Cookie");
        if (cookies != null) {
            for (String cookie : cookies) {
                if (cookie.startsWith("JSESSIONID=")) {
                    return cookie.split(";")[0].split("=")[1];
                }
            }
        }
        return null;
    }
}
