package com.example.volunteer_platform.user_interface;

import com.example.volunteer_platform.controller.AuthenticationController;
import com.example.volunteer_platform.controller.VerificationController;
import com.example.volunteer_platform.model.Customer;
import com.example.volunteer_platform.model.User;
import com.example.volunteer_platform.model.Volunteer;
import com.example.volunteer_platform.utils.CurrentUserContext;
import com.example.volunteer_platform.utils.InputUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.example.volunteer_platform.utils.InputUtils.getUserChoice;

@Component
public class BaseMenuUI {

    private final Scanner scanner;
    private final VolunteerMenuUI volunteerMenuUI;
    private final CustomerMenuUI customerMenuUI;
    private final AuthenticationController authenticationController;
    private final VerificationController verificationController;

    @Autowired
    public BaseMenuUI(VolunteerMenuUI volunteerMenuUI, CustomerMenuUI customerMenuUI,
                      AuthenticationController authenticationController, VerificationController verificationController) {
        this.scanner = new Scanner(System.in);
        this.volunteerMenuUI = volunteerMenuUI;
        this.customerMenuUI = customerMenuUI;
        this.authenticationController = authenticationController;
        this.verificationController = verificationController;
    }

    public void start() {
        while (true) {
            showMainMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    loginAccount();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("Volunteer Platform Console");
        System.out.println("1. Create Account");
        System.out.println("2. Login Account");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }


    private void createAccount() {
        System.out.println("Select account type:");
        System.out.println("1. Volunteer");
        System.out.println("2. Customer");
        int accountType = getUserChoice(scanner);

        switch (accountType) {
            case 1:
                volunteerMenuUI.createVolunteerAccount();
                break;
            case 2:
                customerMenuUI.createCustomerAccount();
                break;
            default:
                System.out.println("Invalid choice. Returning to the main menu.");
                break;
        }
    }

    private void loginAccount() {
        String email = "";

        while (true) {
            email = InputUtils.getValidEmail(verificationController);

            ResponseEntity<String> emailRegisteredCheck = verificationController.verifyAvailableEmail(email);
            if (!emailRegisteredCheck.getStatusCode().is2xxSuccessful()) {
                break;
            } else {
                System.out.println("Email is not registered. Please enter a registered email address.");
            }
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        ResponseEntity<String> response = authenticationController.login(email, password);

        System.out.println(response.getBody());

        if (CurrentUserContext.isAuthenticated()) {
            Class<? extends User> usersRole = CurrentUserContext.getCurrentUser().getClass();
            if (usersRole.equals(Volunteer.class)) {
                volunteerMenuUI.start();
            } else if (usersRole.equals(Customer.class)) {
                customerMenuUI.start();
            }
        }
    }

}
