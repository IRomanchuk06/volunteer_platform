package com.example.volunteer_platform.client.console_ui;

import com.example.volunteer_platform.client.utils.BaseUserMenuUtils;
import com.example.volunteer_platform.client.utils.DisplayFormatter;
import com.example.volunteer_platform.shared_dto.EventRegistrationDTO;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerEventResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.volunteer_platform.client.constants.ApiEndpoints.*;
import static com.example.volunteer_platform.client.constants.CustomerMenuConstants.*;
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
                    case 3:
                        showEventsResponses();
                        break;
                    case 4:
                        sendMessage();
                        break;
                    case 5:
                        checkMailbox();
                        break;
                    case 6:
                        Logout();
                        return;

                    default:
                        System.out.println(INVALID_CHOICE);
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_CHOICE);
            }
        }
    }

    private void Logout() {
        BaseUserMenuUtils.Exit(restTemplateWithCookies);
    }

    private void checkMailbox() {
        BaseUserMenuUtils.checkMailbox(restTemplateWithCookies);
    }

    private void showEventsResponses() {
        System.out.println(VOLUNTEER_RESPONSES);

        try {
            ResponseEntity<List<VolunteerEventResponseDTO>> response = restTemplateWithCookies.exchange(
                    BASE_URL + NOTIFICATIONS_URL + RECEIVED_URL + RESPONSES_URL, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                List<VolunteerEventResponseDTO> volunteerResponses = response.getBody();
                if (volunteerResponses != null && !volunteerResponses.isEmpty()) {
                    volunteerResponses.forEach(volunteerResponse -> System.out.println(
                            DisplayFormatter.formatVolunteerResponseForDisplay(volunteerResponse)));
                } else {
                    System.out.println("Not found");
                }
            } else {
                System.out.println("failed" + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        }

    }

    private void showEvents() {
        BaseUserMenuUtils.showEvents(restTemplateWithCookies);
    }

    private void sendMessage() {
        BaseUserMenuUtils.sendMessage(restTemplateWithCookies);
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

        int numOfRequiredVolunteers = getUserInputPosNum();

        HttpEntity<EventRegistrationDTO> requestEntity = createAddEventRequest(name, description, location, date,
                startTime, endTime, numOfRequiredVolunteers);

        try {
            ResponseEntity<EventResponseDTO> response = restTemplateWithCookies.exchange(
                    BASE_URL + CUSTOMERS_URL + EVENT_URL + CREATE_URL, HttpMethod.POST, requestEntity,
                    EventResponseDTO.class);

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
