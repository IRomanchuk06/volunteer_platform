package com.example.volunteer_platform.client.console_ui;

import com.example.volunteer_platform.client.logging.AppLogger;
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
                        logout();
                        return;

                    default:
                        AppLogger.CLIENT_LOGGER.warn(INVALID_CHOICE);
                }
            } catch (NumberFormatException e) {
                AppLogger.CLIENT_LOGGER.error("Invalid number format: {}", e.getMessage(), e);
            }
        }
    }

    private void logout() {
        AppLogger.CLIENT_LOGGER.info("Logging out...");
        BaseUserMenu.Exit(restTemplateWithCookies);
    }

    private void checkMailbox() {
        AppLogger.CLIENT_LOGGER.info("Checking mailbox...");
        BaseUserMenu.checkMailbox(restTemplateWithCookies);
    }

    private void showEventsResponses() {
        AppLogger.CLIENT_LOGGER.info(VOLUNTEER_RESPONSES);

        try {
            ResponseEntity<List<VolunteerEventResponseDTO>> response = restTemplateWithCookies.exchange(
                    BASE_URL + NOTIFICATIONS_URL + RECEIVED_URL + RESPONSES_URL, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {});

            if (response.getStatusCode().is2xxSuccessful()) {
                List<VolunteerEventResponseDTO> volunteerResponses = response.getBody();
                if (volunteerResponses != null && !volunteerResponses.isEmpty()) {
                    volunteerResponses.forEach(volunteerResponse ->
                            AppLogger.CLIENT_LOGGER.info(DisplayFormatter.formatVolunteerResponseForDisplay(volunteerResponse))
                    );
                } else {
                    AppLogger.CLIENT_LOGGER.warn("No volunteer responses found.");
                }
            } else {
                AppLogger.CLIENT_LOGGER.error("Failed to fetch responses. Status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            AppLogger.CLIENT_LOGGER.error("Error fetching volunteer responses: {}", e.getMessage(), e);
        }
    }

    private void showEvents() {
        AppLogger.CLIENT_LOGGER.info("Fetching events...");
        BaseUserMenu.showEvents(restTemplateWithCookies);
    }

    private void sendMessage() {
        AppLogger.CLIENT_LOGGER.info("Sending message...");
        BaseUserMenu.sendMessage(restTemplateWithCookies);
    }

    private void addEvent() {
        AppLogger.CLIENT_LOGGER.info(ENTER_NAME_PROMPT);
        String name = getUserInputString();

        AppLogger.CLIENT_LOGGER.info(ENTER_DESCRIPTION_PROMPT);
        String description = getUserInputString();

        AppLogger.CLIENT_LOGGER.info(ENTER_LOCATION_PROMPT);
        String location = getUserInputString();

        LocalDate date = getValidDate();
        if (date == null) {
            AppLogger.CLIENT_LOGGER.warn(EXIT_OPERATION_MESSAGE);
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
                AppLogger.CLIENT_LOGGER.info(EVENT_ADDED_SUCCESS);
            } else {
                AppLogger.CLIENT_LOGGER.error(FAILED_TO_ADD_EVENT + "{}", response.getStatusCode());
            }
        } catch (Exception e) {
            AppLogger.CLIENT_LOGGER.error(EVENT_ADD_ERROR + "{}", e.getMessage(), e);
        }
    }

    private void showMenu() {
        AppLogger.CLIENT_LOGGER.info(CUSTOMER_MENU_TITLE);
        AppLogger.CLIENT_LOGGER.info(CUSTOMER_MENU_OPTIONS);
    }
}
