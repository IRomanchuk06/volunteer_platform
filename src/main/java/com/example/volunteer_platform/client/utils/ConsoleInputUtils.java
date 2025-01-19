package com.example.volunteer_platform.client.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleInputUtils {

    public static int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose an option: ");
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("No input entered. Please enter a valid option.");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error reading input. Exiting...");
                return -1;
            }
        }
    }

    /*
        public static String getValidEmail(RestTemplate restTemplate, String baseUrl) {
        String email;
        while (true) {
            System.out.print(MenuConstants.ENTER_EMAIL_PROMPT);
            email = scanner.nextLine();

            if (email.equalsIgnoreCase("exit")) {
                System.out.println(MenuConstants.EXIT_OPERATION_MESSAGE);
                return null;
            }

            String url = baseUrl + ApiEndpoints.EMAIL_VALIDATION_URL + email;
            ResponseEntity<String> emailValidationResponse = restTemplate.getForEntity(url, String.class);
            if (!emailValidationResponse.getStatusCode().is2xxSuccessful()) {
                System.out.println(MenuConstants.INVALID_EMAIL_FORMAT);
                continue;
            }

            return email;
        }
    }

    public static String getRegistrationEmail(RestTemplate restTemplate, String baseUrl) {
        String email = "";
        while (true) {
            email = getValidEmail(restTemplate, baseUrl);

            String url = baseUrl + ApiEndpoints.EMAIL_CHECK_AVAILABILITY_URL + email;
            ResponseEntity<String> emailRegisteredCheck = restTemplate.getForEntity(url, String.class);
            if (emailRegisteredCheck.getStatusCode().is2xxSuccessful()) {
                break;
            } else {
                System.out.println(MenuConstants.EMAIL_ALREADY_REGISTERED);
            }
        }
        return email;
    }
     */


}
