package com.example.volunteer_platform.client.utils;

import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.MessageRegistrationDTO;
import com.example.volunteer_platform.shared_dto.NotificationResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.volunteer_platform.client.constants.ApiEndpoints.*;
import static com.example.volunteer_platform.client.constants.BaseUserMenuConstants.*;
import static com.example.volunteer_platform.client.request_builder.MessageRequestBuilder.createMessageRequest;

public class BaseUserMenuUtils {
    public static void showEvents(RestTemplate restTemplateWithCookies) {
        System.out.println(EVENTS_LIST_TITLE);
        String url = BASE_URL + EVENT_URL;
        try {
            ResponseEntity<List<EventResponseDTO>> response = restTemplateWithCookies.exchange(url, HttpMethod.GET,
                    null, new ParameterizedTypeReference<>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                List<EventResponseDTO> events = response.getBody();
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

    public static void sendMessage(RestTemplate restTemplateWithCookies) {
        System.out.println(RECIPIENT_PROMPT);
        String recipientEmail = ConsoleInputUtils.getValidEmail(BASE_URL);

        System.out.println(MESSAGE_PROMPT);
        String message = ConsoleInputUtils.getUserInputString();

        String url = BASE_URL + USERS_URL + MESSAGES_URL + CREATE_URL;
        HttpEntity<MessageRegistrationDTO> requestEntity = createMessageRequest(message, recipientEmail);
        try {
            ResponseEntity<NotificationResponseDTO> response = restTemplateWithCookies.exchange(url, HttpMethod.POST,
                    requestEntity, NotificationResponseDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println(MESSAGE_SENT_SUCCESS);
            } else {
                System.out.println(MESSAGE_SENT_FAILED + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println(MESSAGE_SENT_ERROR + e.getMessage());
        }
    }

    public static void checkMailbox(RestTemplate restTemplateWithCookies) {
        System.out.println(MAILBOX_TITLE);
        String url = BASE_URL + NOTIFICATIONS_URL + RECEIVED_URL + MESSAGES_URL;
        try {
            ResponseEntity<List<NotificationResponseDTO>> response = restTemplateWithCookies.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                List<NotificationResponseDTO> messages = response.getBody();
                if (messages != null && !messages.isEmpty()) {
                    messages.forEach(
                            message -> System.out.println(DisplayFormatter.formatNotificationForDisplay(message)));
                } else {
                    System.out.println(NO_MESSAGES_FOUND);
                }
            } else {
                System.out.println(MAILBOX_FAILED + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println(MAILBOX_ERROR + e.getMessage());
        }
    }
}
