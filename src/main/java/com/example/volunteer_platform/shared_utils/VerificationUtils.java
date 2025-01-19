package com.example.volunteer_platform.shared_utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationUtils {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
