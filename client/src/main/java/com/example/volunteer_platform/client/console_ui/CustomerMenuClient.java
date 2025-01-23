package com.example.volunteer_platform.client.console_ui;

import com.example.volunteer_platform.client.utils.DisplayFormatter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static com.example.volunteer_platform.client.constants.ApiEndpoints.*;
import static com.example.volunteer_platform.client.constants.CustomerMenuConstants.*;
import static com.example.volunteer_platform.client.constants.MenuConstants.EXIT_OPERATION_MESSAGE;
import static com.example.volunteer_platform.client.constants.MenuConstants.INVALID_CHOICE;
import static com.example.volunteer_platform.client.request_builder.EventRequestBuilder.createAddEventRequest;
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
                    case 2:
                        showEvents();
                        break;

                    default:
                        System.out.println(INVALID_CHOICE);
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_CHOICE);
            }
        }
    }

    private void showEvents() {
        System.out.println(EVENTS_LIST_TITLE);
        String url = BASE_URL + EVENT_URL;
        try {
            ResponseEntity<List<Event>> response = restTemplateWithCookies.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                List<Event> events = response.getBody();
                if (events != null && !events.isEmpty()) {
                    events.forEach(event -> System.out.println(DisplayFormatter.formatEventForDisplay(event)));
                } else {
                    System.out.println(NO_EVENTS_FOUND);
                }
            } else {
                System.out.println(FAILED_TO_FETCH_EVENTS + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println(EVENT_FETCH_ERROR + e.getMessage());
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

        if (date == null) {
            System.out.println(EXIT_OPERATION_MESSAGE);
            return;
        }

        LocalTime startTime = getValidStartTime();

        LocalTime endTime = getValidEndTime();

        int numOfRequiredVolunteers = 2;
        int numOfRespondingVolunteers = 3;

        HttpEntity<String> requestEntity = createAddEventRequest(name, description, location, date,
                startTime, endTime, numOfRequiredVolunteers, numOfRespondingVolunteers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplateWithCookies.exchange(
                    BASE_URL + CUSTOMERS_URL + EVENT_URL + CREATE_URL, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println(EVENT_ADDED_SUCCESS);
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
