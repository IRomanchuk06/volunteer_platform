package com.example.volunteer_platform.utils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputUtils {
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
}
