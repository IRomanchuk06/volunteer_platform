package com.example.volunteer_platform.client.console_ui;

import com.example.volunteer_platform.client.utils.ConsoleInputUtils;
import com.example.volunteer_platform.client.utils.DisplayFormatter;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.MessageRegistrationDTO;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.volunteer_platform.client.constants.ApiEndpoints.*;
import static com.example.volunteer_platform.client.constants.BaseUserMenuConstants.*;
import static com.example.volunteer_platform.client.request_builder.MessageRequestBuilder.createMessageRequest;

public class BaseUserMenu {

    private static final Logger logger = LoggerFactory.getLogger(BaseUserMenu.class);

    public static void showEvents(RestTemplate restTemplateWithCookies) {
        logger.info(EVENTS_LIST_TITLE);
        String url = BASE_URL + EVENT_URL;
        try {
            ResponseEntity<List<EventResponseDTO>> response = restTemplateWithCookies.exchange(url, HttpMethod.GET,
                    null, new ParameterizedTypeReference<>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                List<EventResponseDTO> events = response.getBody();
                if (events != null && !events.isEmpty()) {
                    events.forEach(event -> logger.info(DisplayFormatter.formatEventForDisplay(event)));
                } else {
                    logger.info(NO_EVENTS_FOUND);
                }
            } else {
                logger.error(FAILED_TO_FETCH_EVENTS + "{}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error(EVENT_FETCH_ERROR, e);
        }
    }

    public static void sendMessage(RestTemplate restTemplateWithCookies) {
        logger.info(RECIPIENT_PROMPT);
        String recipientEmail = ConsoleInputUtils.getValidEmail(BASE_URL);

        logger.info(MESSAGE_PROMPT);
        String message = ConsoleInputUtils.getUserInputString();

        String url = BASE_URL + USERS_URL + MESSAGES_URL + CREATE_URL;
        HttpEntity<MessageRegistrationDTO> requestEntity = createMessageRequest(message, recipientEmail);
        try {
            ResponseEntity<MessageResponseDTO> response = restTemplateWithCookies.exchange(url, HttpMethod.POST,
                    requestEntity, MessageResponseDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info(MESSAGE_SENT_SUCCESS);
            } else {
                logger.error(MESSAGE_SENT_FAILED + "{}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error(MESSAGE_SENT_ERROR, e);
        }
    }

    public static void checkMailbox(RestTemplate restTemplateWithCookies) {
        logger.info(MAILBOX_TITLE);
        String url = BASE_URL + NOTIFICATIONS_URL + RECEIVED_URL + MESSAGES_URL;
        try {
            ResponseEntity<List<MessageResponseDTO>> response = restTemplateWithCookies.exchange(url, HttpMethod.GET,
                    null, new ParameterizedTypeReference<>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                List<MessageResponseDTO> messages = response.getBody();
                if (messages != null && !messages.isEmpty()) {
                    messages.forEach(
                            message -> logger.info(DisplayFormatter.formatNotificationForDisplay(message)));
                } else {
                    logger.info(NO_MESSAGES_FOUND);
                }
            } else {
                logger.error(MAILBOX_FAILED + "{}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error(MAILBOX_ERROR, e);
        }
    }

    public static void Exit(RestTemplate restTemplateWithCookies) {
        try {
            ResponseEntity<Boolean> response = restTemplateWithCookies.exchange(BASE_URL + LOGOUT_URL,
                    HttpMethod.POST, null, Boolean.class);
            if(response.getStatusCode().is2xxSuccessful()) {
                logger.info("Logout successful");
            } else {
                logger.error("Logout failed");
            }
        } catch (Exception e) {
            logger.error("Logout error", e);
        }
    }
}
