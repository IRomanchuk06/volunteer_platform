package com.example.volunteer_platform.user_interface;

import com.example.volunteer_platform.controller.VolunteerController;
import com.example.volunteer_platform.exeptions.EmailAlreadyExistsException;
import com.example.volunteer_platform.exeptions.InvalidEmailException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class VolunteerMenuUI {

    private final Scanner scanner;
    private final VolunteerController volunteerController;

    public VolunteerMenuUI(VolunteerController volunteerController) {
        this.volunteerController = volunteerController;
        this.scanner = new Scanner(System.in);
    }

    public void createVolunteerAccount() {
        System.out.println("Create Volunteer Account");

        System.out.print("Enter email: ");
        String email = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        System.out.print("Enter username: ");
        String username = scanner.next();

        try {
            volunteerController.createVolunteer(email, password, username);
        } catch (EmailAlreadyExistsException | InvalidEmailException e) {
            System.out.print(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred", e);
        }

        System.out.println("Volunteer account created successfully.");
    }
}
