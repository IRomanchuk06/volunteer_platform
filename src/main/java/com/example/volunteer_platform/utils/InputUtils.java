package com.example.volunteer_platform.utils;

import com.example.volunteer_platform.controller.VerificationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getUserChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            if (!scanner.hasNextLine()) {
                System.out.println("No input available. Exiting.");
                return -1;
            }
            System.out.println("Invalid input. Please enter a valid option.");
            scanner.nextLine();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getValidEmail(VerificationController verificationController) {
        String email;
        while (true) {
            System.out.print("Enter email (or type 'exit' to quit): ");
            email = scanner.nextLine();

            if (email.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the operation.");
                return null;
            }

            ResponseEntity<String> emailValidationResponse = verificationController.verifyValidEmail(email);
            if (!emailValidationResponse.getStatusCode().is2xxSuccessful()) {
                System.out.println("Invalid email format. Please enter a valid email address.");
                continue;
            }

            return email;
        }
    }

    public static String getRegistrationEmail(VerificationController verificationController) {
        String email = "";
        while (true) {
            email = InputUtils.getValidEmail(verificationController);

            ResponseEntity<String> emailRegisteredCheck = verificationController.verifyAvailableEmail(email);
            if (emailRegisteredCheck.getStatusCode().is2xxSuccessful()) {
                break;
            } else {
                System.out.println("Email is already registered. Please enter a different email address.");
            }
        }
        return email;
    }
}
