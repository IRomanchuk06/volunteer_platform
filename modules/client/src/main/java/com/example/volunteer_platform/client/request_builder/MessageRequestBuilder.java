package com.example.volunteer_platform.client.request_builder;

import com.example.volunteer_platform.shared_dto.MessageRegistrationDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.example.volunteer_platform.client.utils.RequestBuilderUtils.validateNonNull;

public class MessageRequestBuilder {
    public static HttpEntity<MessageRegistrationDTO> createMessageRequest(String message, String recipientEmail) {
        validateNonNull(message, recipientEmail);

        MessageRegistrationDTO eventRequest = new MessageRegistrationDTO(message, recipientEmail);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(eventRequest, headers);
    }
}
