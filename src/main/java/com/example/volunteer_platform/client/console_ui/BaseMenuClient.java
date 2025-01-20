package com.example.volunteer_platform.client.console_ui;

import com.example.volunteer_platform.client.utils.AccountType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.example.volunteer_platform.client.constants.ApiEndpoints.*;
import static com.example.volunteer_platform.client.constants.MenuConstants.*;
import static com.example.volunteer_platform.client.utils.AccountRequestBuilder.*;
import static com.example.volunteer_platform.client.utils.ConsoleInputUtils.*;

@Component
public class BaseMenuClient {

    private final VolunteerMenuClient volunteerMenuClient;
    private final CustomerMenuClient customerMenuClient;
    private final RestTemplate restTemplate;

    public BaseMenuClient(VolunteerMenuClient volunteerMenuClient, CustomerMenuClient customerMenuClient) {
        this.volunteerMenuClient = volunteerMenuClient;
        this.customerMenuClient = customerMenuClient;
        this.restTemplate = new RestTemplate();
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

        AccountType accountType = AccountType.fromValue(getUserChoice());

        String email = getRegistrationEmail(restTemplate, BASE_URL);

        if (email == null) {
            System.out.println(EXIT_OPERATION_MESSAGE);
            return;
        }

        System.out.println(ENTER_PASSWORD_PROMPT);
        String password = getUserInputString();

        System.out.println(ENTER_USERNAME_PROMPT);
        String username = getUserInputString();

        HttpEntity<String> requestEntity = null;
        String accountTypeUrl = null;

        switch (accountType) {
            case VOLUNTEER:
                requestEntity = createRegistrationRequest(email,password,username, VOLUNTEER_URL);
                accountTypeUrl = VOLUNTEER_URL;
                break;
            case CUSTOMER:
                requestEntity = createRegistrationRequest(email,password,username, CUSTOMER_URL);
                accountTypeUrl = CUSTOMER_URL;
                break;
            default:
                System.out.println(INVALID_ACCOUNT_CHOICE);
                break;
        }

        try {
            String targetUrl = BASE_URL + accountTypeUrl;

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    targetUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println(ACCOUNT_CREATION_SUCCESS);
            } else {
                System.out.println(ACCOUNT_CREATION_FAILED + response.getStatusCode());
                System.out.println(RESPONSE_BODY + response.getBody());
            }
        } catch (Exception e) {
            System.out.println(ACCOUNT_CREATION_ERROR + e.getMessage());
        }
    }

    private void loginAccount() {
        String email = getValidEmail(restTemplate, BASE_URL);

        if (email == null) {
            System.out.println(EXIT_OPERATION_MESSAGE);
            return;
        }

        System.out.println(ENTER_PASSWORD_PROMPT);
        String password = getUserInputString();

        HttpEntity<String> requestEntity = createLoginRequest(email, password);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    BASE_URL + LOGIN_URL,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                System.out.println(LOGIN_FAILED + response.getStatusCode());
                return;
            }

            System.out.println(LOGIN_SUCCESS);

            Map<String, Object> responseBody = response.getBody();

            if (responseBody == null) {
                throw new IllegalStateException(NULL_RESPONSE_BODY);
            }

            String accountType = (String) responseBody.get(ROLE);

            if (VOLUNTEER.equals(accountType)) {
                volunteerMenuClient.start();
            } else if (CUSTOMER.equals(accountType)) {
                customerMenuClient.start();
            } else {
                throw new IllegalArgumentException(INVALID_ACCOUNT_TYPE + accountType);
            }

        } catch (Exception e) {
            System.out.println(LOGIN_ERROR + e.getMessage());
        }
    }
}
