package com.example.volunteer_platform.client.console_ui;

import com.example.volunteer_platform.client.constants.ApiEndpoints;
import com.example.volunteer_platform.client.constants.MenuConstants;
import com.example.volunteer_platform.client.utils.ConsoleInputUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.example.volunteer_platform.client.utils.ConsoleInputUtils.getUserChoice;

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
                        System.out.println(MenuConstants.INVALID_CHOICE);
                }
            } catch (NumberFormatException e) {
                System.out.println(MenuConstants.INVALID_CHOICE);
            }
        }
    }

    private void logoutAccount() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                ApiEndpoints.BASE_URL + ApiEndpoints.LOGOUT_URL,
                HttpMethod.POST,
                HttpEntity.EMPTY,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println(MenuConstants.EXIT_MESSAGE);
        } else {
            System.out.println(MenuConstants.EXIT_ERROR);
        }
    }

    private void showMenu() {
        System.out.println(MenuConstants.MAIN_MENU_TITLE);
        System.out.println(MenuConstants.MAIN_MENU_OPTIONS);
    }

    private void createAccount() {
        System.out.println(MenuConstants.SELECT_ACCOUNT_TYPE);
        System.out.println(MenuConstants.ACCOUNT_TYPE_OPTIONS);
        int accountType = ConsoleInputUtils.getUserChoice();

        switch (accountType) {
            case 1:
                volunteerMenuClient.createVolunteerAccount();
                break;
            case 2:
                customerMenuClient.createCustomerAccount();
                break;
            default:
                System.out.println(MenuConstants.INVALID_ACCOUNT_TYPE);
                break;
        }
    }

    private void loginAccount() {

    }
}
