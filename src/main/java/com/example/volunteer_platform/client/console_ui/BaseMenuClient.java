package com.example.volunteer_platform.client.console_ui;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.example.volunteer_platform.client.constants.ApiEndpoints.*;
import static com.example.volunteer_platform.client.constants.MenuConstants.*;
import static com.example.volunteer_platform.client.utils.ConsoleInputUtils.*;

@Component
public class BaseMenuClient {

    private final VolunteerMenuClient volunteerMenuClient;
    private final CustomerMenuClient customerMenuClient;

    public BaseMenuClient(VolunteerMenuClient volunteerMenuClient, CustomerMenuClient customerMenuClient) {
        this.volunteerMenuClient = volunteerMenuClient;
        this.customerMenuClient = customerMenuClient;
    }

    public void start() {
        while (true) {
            showMenu();

            int choice = getUserChoice();

            try {

                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        loginAccount();
                        break;
                    case 3:
                        logoutAccount();
                        return;
                    default:
                        System.out.println(INVALID_CHOICE);
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_CHOICE);
            }
        }
    }

    private void logoutAccount() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + LOGOUT_URL,
                HttpMethod.POST,
                HttpEntity.EMPTY,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println(EXIT_MESSAGE);
        } else {
            System.out.println(EXIT_ERROR);
        }
    }

    private void showMenu() {
        System.out.println(MAIN_MENU_TITLE);
        System.out.println(MAIN_MENU_OPTIONS);
    }

    private void createAccount() {
        System.out.println(SELECT_ACCOUNT_TYPE);
        System.out.println(ACCOUNT_TYPE_OPTIONS);
        int accountType = getUserChoice();

        switch (accountType) {
            case 1:
                volunteerMenuClient.createVolunteerAccount();
                break;
            case 2:
                customerMenuClient.createCustomerAccount();
                break;
            default:
                System.out.println(INVALID_ACCOUNT_TYPE);
                break;
        }
    }

    private void loginAccount() {
        String email = "";
        RestTemplate restTemplate = new RestTemplate();

        email = getRegistrationEmail(restTemplate, BASE_URL);

        if (email == null) {
            System.out.println(EXIT_OPERATION_MESSAGE);
            return;
        }

        System.out.println(ENTER_PASSWORD_PROMPT);
        String password = getUserInputString();

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", email);
        loginRequest.put("password", password);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(loginRequest);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    BASE_URL + LOGIN_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println(LOGIN_SUCCESS);
            } else {
                System.out.println(LOGIN_FAILED + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println(LOGIN_ERROR + e.getMessage());
        }
    }
}
