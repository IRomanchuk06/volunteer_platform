package com.example.volunteer_platform.client.console_ui;

import com.example.volunteer_platform.client.constants.UtilsConstants;
import com.example.volunteer_platform.client.utils.BaseUserMenuUtils;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.example.volunteer_platform.client.constants.VolunteerMenuConstants.*;
import static com.example.volunteer_platform.client.constants.ApiEndpoints.*;
import static com.example.volunteer_platform.client.utils.ConsoleInputUtils.getUserChoice;
import static com.example.volunteer_platform.client.utils.ConsoleInputUtils.getUserInputLong;

@Component
public class VolunteerMenuClient {
    private RestTemplate restTemplateWithCookies;

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

                    default:
                        System.out.println(INVALID_CHOICE);
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_CHOICE);
            }
        }
    }

    private void checkMailbox() {
        BaseUserMenuUtils.checkMailbox(restTemplateWithCookies);
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
                System.out.println(EVENT_RESPONSE_SUCCESS);
            } else {
                System.out.println(EVENT_RESPONSE_ERROR + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println(EVENT_RESPONSE_ERROR + e.getMessage());
        }
    }

    private void showEvents() {
        BaseUserMenuUtils.showEvents(restTemplateWithCookies);
    }

    private void sendMessage() {
        BaseUserMenuUtils.sendMessage(restTemplateWithCookies);
    }

    private void showMenu() {
        System.out.println(VOLUNTEER_MENU_TITLE);
        System.out.println(VOLUNTEER_MENU_OPTIONS);
    }
}
