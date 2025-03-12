package com.example.volunteer_platform.server.utils;

public class StringUtils {
    public static String maskEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "unknown";
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return email;
        }
        return email.charAt(0) + "***" + email.substring(atIndex);
    }
}
