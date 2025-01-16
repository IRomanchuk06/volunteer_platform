package com.example.volunteer_platform.user_interface;

import com.example.volunteer_platform.controller.VerificationController;
import com.example.volunteer_platform.controller.VolunteerController;
import com.example.volunteer_platform.model.Volunteer;
import com.example.volunteer_platform.utils.InputUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class VolunteerMenuUI {

    private final Scanner scanner;
    private final VolunteerController volunteerController;
    private final VerificationController verificationController;

    public VolunteerMenuUI(VolunteerController volunteerController, VerificationController verificationController) {
        this.volunteerController = volunteerController;
        this.verificationController = verificationController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("Volunteer Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void createVolunteerAccount() {
        System.out.println("Create Volunteer Account");

        String email = InputUtils.getRegistrationEmail(verificationController);

        System.out.print("Enter password: ");
        String password = scanner.next();

        System.out.print("Enter username: ");
        String username = scanner.next();

        ResponseEntity<Volunteer> response = volunteerController.createVolunteer(email, password, username);

        // response handling

        System.out.println(response.getBody());
    }
}
