package com.example.volunteer_platform.user_interface;

import com.example.volunteer_platform.controller.UserController;
import com.example.volunteer_platform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.example.volunteer_platform.utils.InputUtils.getUserChoice;

@Component
public class BaseMenuUI {

    private final Scanner scanner;
    private final VolunteerMenuUI volunteerMenuUI;
    private final CustomerMenuUI customerMenuUI;
    private final UserController<User> userController;

    @Autowired
    public BaseMenuUI(VolunteerMenuUI volunteerMenuUI, CustomerMenuUI customerMenuUI, UserController<User> userController) {
        this.scanner = new Scanner(System.in);
        this.volunteerMenuUI = volunteerMenuUI;
        this.customerMenuUI = customerMenuUI;
        this.userController = userController;
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
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userController.authenticate(email, password);

        if (user != null) {
            System.out.println("Login successful! Welcome " + user.getUsername());
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }
}
