package com.example.volunteer_platform.client.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static com.example.volunteer_platform.client.constants.ApiEndpoints.EMAIL_CHECK_AVAILABILITY_URL;
import static com.example.volunteer_platform.client.constants.ApiEndpoints.EMAIL_VALIDATION_URL;
import static com.example.volunteer_platform.client.constants.UtilsConstants.*;

public class ConsoleInputUtils {

    private static final RestTemplate consoleUtilsRestTemplate = new RestTemplate();

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
                System.out.println(INVALID_CHOICE + e.getMessage());
            } catch (Exception e) {
                System.out.println(READING_ERROR + e.getMessage());
                return -1;
            }
        }
    }

    public static Long getUserInputLong() {
        while (true) {
            System.out.println(ENTER_EVENT_ID_PROMPT);
            String input = getUserInputString();

            if (input.isEmpty()) {
                System.out.println(NO_INPUT);
                continue;
            }

            if (EXIT.equals(input)) {
                return null;
            }

            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println(INVALID_NUMBER_FORMAT + e.getMessage());
            }
        }
    }

    public static int getUserInputPosNum() {
        while (true) {
            System.out.println(ENTER_POSITIVE_NUMBER_PROMPT);
            String input = getUserInputString();

            if (input.isEmpty()) {
                System.out.println(NO_INPUT);
                continue;
            }

            try {
                int number = Integer.parseInt(input);
                if (number > 0) {
                    return number;
                } else {
                    System.out.println(INVALID_POSITIVE_NUMBER);
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_NUMBER_FORMAT + e.getMessage());
            }
        }
    }

    public static String getValidEmail(String baseUrl) {
        String email;
        while (true) {
            System.out.println(ENTER_EMAIL_PROMPT);
            email = getUserInputString();

            if (EXIT.equalsIgnoreCase(email)) {
                return null;
            }

            String url = baseUrl + EMAIL_VALIDATION_URL + email;

            try {
                ResponseEntity<String> emailValidationResponse = consoleUtilsRestTemplate.getForEntity(url, String.class);
                if (!emailValidationResponse.getStatusCode().is2xxSuccessful()) {
                    System.out.println(INVALID_EMAIL_FORMAT);
                    continue;
                }
            } catch (Exception e) {
                System.out.println(EMAIL_VALIDATING_ERROR + e.getMessage());
                continue;
            }

            return email;
        }
    }

    public static String getRegistrationEmail(String baseUrl) {
        String email;
        while (true) {
            email = getValidEmail(baseUrl);

            if (email == null) {
                return null;
            }

            String url = baseUrl + EMAIL_CHECK_AVAILABILITY_URL + email;
            try {
                ResponseEntity<String> emailRegisteredCheck = consoleUtilsRestTemplate.getForEntity(url, String.class);

                if (emailRegisteredCheck.getStatusCode().is2xxSuccessful()) {
                    break;
                } else {
                    System.out.println(EMAIL_ALREADY_REGISTERED);
                }
            } catch (Exception e) {
                System.out.println(EMAIL_AVAILABILITY_ERROR + e.getMessage());
            }
        }
        return email;
    }

    public static LocalDate getValidDate() {
        Scanner scanner = new Scanner(System.in);
        LocalDate date;
        while (true) {
            System.out.println(ENTER_DATE_PROMPT);
            String dateInput = scanner.nextLine();

            if (EXIT.equalsIgnoreCase(dateInput)) {
                return null;
            }

            try {
                date = LocalDate.parse(dateInput);
                return date;
            } catch (DateTimeParseException e) {
                System.out.println(INVALID_DATE_FORMAT + e.getMessage());
            }
        }
    }

    private static LocalTime getValidTime(String message) {
        Scanner scanner = new Scanner(System.in);
        LocalTime time;
        while (true) {
            System.out.println(message);
            String timeInput = scanner.nextLine();

            if (EXIT.equalsIgnoreCase(timeInput)) {
                return null;
            }

            if(timeInput.isBlank()) {
                return null;
            }

            try {
                time = LocalTime.parse(timeInput);
                return time;
            } catch (DateTimeParseException e) {
                System.out.println(INVALID_TIME_FORMAT + e.getMessage());
            }
        }
    }

    public static LocalTime getValidStartTime() {
        return getValidTime(ENTER_START_TIME_PROMPT);
    }

    public static LocalTime getValidEndTime() {
        return getValidTime(ENTER_END_TIME_PROMPT);
    }
}
