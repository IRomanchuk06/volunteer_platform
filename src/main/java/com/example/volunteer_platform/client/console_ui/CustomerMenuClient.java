package com.example.volunteer_platform.client.console_ui;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static com.example.volunteer_platform.client.constants.CustomerMenuConstants.*;
import static com.example.volunteer_platform.client.constants.MenuConstants.INVALID_CHOICE;
import static com.example.volunteer_platform.client.constants.ApiEndpoints.*;
import static com.example.volunteer_platform.client.constants.MenuConstants.RESPONSE_BODY;
import static com.example.volunteer_platform.client.utils.AccountRequestBuilder.createAddEventRequest;
import static com.example.volunteer_platform.client.utils.ConsoleInputUtils.*;

@Component
public class CustomerMenuClient {

    private RestTemplate restTemplateWithCookies;

    public void start(RestTemplate restTemplateWithCookies) {
        this.restTemplateWithCookies = restTemplateWithCookies;
        while (true) {
            showMenu();

            int choice = getUserChoice();

            try {

                switch (choice) {
                    case 1:
                        addEvent();
                        break;
                    default:
                        System.out.println(INVALID_CHOICE);
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_CHOICE);
            }
        }
    }

    private void addEvent() {
        System.out.println(ENTER_NAME_PROMPT);
        String name = getUserInputString();

        System.out.println(ENTER_DESCRIPTION_PROMPT);
        String description = getUserInputString();

        System.out.println(ENTER_LOCATION_PROMPT);
        String location = getUserInputString();

        LocalDate date = getValidDate();

        LocalTime startTime = getValidStartTime();

        LocalTime endTime = getValidEndTime();

        HttpEntity<String> requestEntity = createAddEventRequest(name, description, location, date, startTime, endTime);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplateWithCookies.exchange(
                    BASE_URL + CUSTOMERS_URL + ADD_EVENT_URL,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println(EVENT_ADDED_SUCCESS);
                System.out.println(RESPONSE_BODY + response.getBody());
            } else {
                System.out.println(FAILED_TO_ADD_EVENT + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println(EVENT_ADD_ERROR + e.getMessage());
        }
    }

    private void showMenu() {
        System.out.println(CUSTOMER_MENU_TITLE);
        System.out.println(CUSTOMER_MENU_OPTIONS);
    }
}
