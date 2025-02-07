package com.example.volunteer_platform.client.console_ui;

import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import static com.example.volunteer_platform.client.constants.VolunteerMenuConstants.*;
import static com.example.volunteer_platform.client.constants.ApiEndpoints.*;
import static com.example.volunteer_platform.client.utils.ConsoleInputUtils.getUserChoice;
import static com.example.volunteer_platform.client.utils.ConsoleInputUtils.getUserInputLong;
import com.example.volunteer_platform.client.logging.AppLogger;

@Component
public class VolunteerMenuClient {
    private RestTemplate restTemplateWithCookies;
    private static final org.slf4j.Logger logger = AppLogger.CLIENT_LOGGER;

    public void start(RestTemplate restTemplateWithCookies) {
        this.restTemplateWithCookies = restTemplateWithCookies;
        while (true) {
            showMenu();

            int choice = getUserChoice();

            try {
                switch (choice) {
                    case 1:
                        responseToEvent();
                        break;
                    case 2:
                        showEvents();
                        break;
                    case 3:
                        sendMessage();
                        break;
                    case 4:
                        checkMailbox();
                        break;
                    case 5:
                        Logout();
                        return;

                    default:
                        logger.warn(INVALID_CHOICE);
                }
            } catch (NumberFormatException e) {
                logger.error(INVALID_CHOICE, e);
            }
        }
    }

    private void Logout() {
        BaseUserMenu.Exit(restTemplateWithCookies);
    }

    private void checkMailbox() {
        BaseUserMenu.checkMailbox(restTemplateWithCookies);
    }

    private void responseToEvent() {
        Long eventId = getUserInputLong();

        if (eventId == null) {
            return;
        }

        try {
            ResponseEntity<EventResponseDTO> response = restTemplateWithCookies.exchange(
                    BASE_URL + VOLUNTEERS_URL + RESPONSE_TO_EVENT_URL + eventId, HttpMethod.POST, null,
                    EventResponseDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info(EVENT_RESPONSE_SUCCESS);
            } else {
                logger.error(EVENT_RESPONSE_ERROR + "{}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error(EVENT_RESPONSE_ERROR, e);
        }
    }

    private void showEvents() {
        BaseUserMenu.showEvents(restTemplateWithCookies);
    }

    private void sendMessage() {
        BaseUserMenu.sendMessage(restTemplateWithCookies);
    }

    private void showMenu() {
        logger.info(VOLUNTEER_MENU_TITLE);
        logger.info(VOLUNTEER_MENU_OPTIONS);
    }
}
