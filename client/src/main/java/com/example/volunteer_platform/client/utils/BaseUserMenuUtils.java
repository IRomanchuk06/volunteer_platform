package com.example.volunteer_platform.client.utils;

import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.volunteer_platform.client.constants.ApiEndpoints.BASE_URL;
import static com.example.volunteer_platform.client.constants.ApiEndpoints.EVENT_URL;
import static com.example.volunteer_platform.client.constants.CustomerMenuConstants.*;
import static com.example.volunteer_platform.client.constants.CustomerMenuConstants.EVENT_FETCH_ERROR;

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
}
