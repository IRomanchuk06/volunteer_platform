package com.example.volunteer_platform.user_interface;

import com.example.volunteer_platform.controller.CustomerController;
import com.example.volunteer_platform.controller.VerificationController;
import com.example.volunteer_platform.model.Customer;
import com.example.volunteer_platform.utils.InputUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CustomerMenuUI {

    private final CustomerController customerController;
    private final VerificationController verificationController;
    private final Scanner scanner;

    @Autowired
    public CustomerMenuUI(CustomerController customerController, VerificationController verificationController) {
        this.customerController = customerController;
        this.verificationController = verificationController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("Customer Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void createCustomerAccount() {
        System.out.println("Create Customer Account");

        String email = InputUtils.getRegistrationEmail(verificationController);

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        ResponseEntity<Customer> response = customerController.createCustomer(email, password, username);

        // response handling

        System.out.println(response.getBody());
    }

}
