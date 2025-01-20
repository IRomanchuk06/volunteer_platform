package com.example.volunteer_platform.client.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

import static com.example.volunteer_platform.client.constants.ApiEndpoints.*;
import static com.example.volunteer_platform.client.constants.MenuConstants.*;

public class ConsoleInputUtils {

    public static String getUserInputString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    public static int getUserChoice() {
        while (true) {
            System.out.println(CHOOSE_OPTION);
            String input = getUserInputString();
            if (input.isEmpty()) {
                System.out.println(NO_INPUT);
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(INVALID_CHOICE);
            } catch (Exception e) {
                System.out.println(READING_ERROR);
                return -1;
            }
        }
    }

    public static String getValidEmail(RestTemplate restTemplate, String baseUrl) {
        String email;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(ENTER_EMAIL_PROMPT);

            email = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(email)) {
                System.out.println(EXIT_OPERATION_MESSAGE);
                return null;
            }

            String url = baseUrl + EMAIL_VALIDATION_URL + email;

            try {
                ResponseEntity<String> emailValidationResponse = restTemplate.getForEntity(url, String.class);
                if (!emailValidationResponse.getStatusCode().is2xxSuccessful()) {
                    System.out.println(INVALID_EMAIL_FORMAT);
                    continue;
                }
            } catch (Exception e) {
                System.out.println(EMAIL_VALIDATING_ERROR);
                continue;
            }

            return email;
        }
    }

    public static String getRegistrationEmail(RestTemplate restTemplate, String baseUrl) {
        String email;
        while (true) {
            email = getValidEmail(restTemplate, baseUrl);

            if (email == null) {
                return null;
            }

            String url = baseUrl + EMAIL_CHECK_AVAILABILITY_URL + email;
            try {
                ResponseEntity<String> emailRegisteredCheck = restTemplate.getForEntity(url, String.class);

                if (emailRegisteredCheck.getStatusCode().is2xxSuccessful()) {
                    break;
                } else {
                    System.out.println(EMAIL_ALREADY_REGISTERED);
                }
            } catch (Exception e) {
                System.out.println(EMAIL_AVAILABILITY_ERROR);
            }
        }
        return email;
    }
}
