package com.example.volunteer_platform.client.utils;

import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.volunteer_platform.client.constants.UtilsConstants.*;


public class CookieUtils {

    public static String getSessionIdFromResponse(ResponseEntity<?> response) {
        List<String> cookies = response.getHeaders().get(SET_COOKIE_HEADER);
        if (cookies != null) {
            for (String cookie : cookies) {
                if (cookie.startsWith(SESSION_ID_COOKIE_NAME)) {
                    return cookie.split(COOKIE_SEPARATOR)[0].split(COOKIE_VALUE_SEPARATOR)[1];
                }
            }
        }
        return null;
    }
}
