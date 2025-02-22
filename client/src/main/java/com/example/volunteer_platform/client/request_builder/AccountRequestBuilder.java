package com.example.volunteer_platform.client.request_builder;

import com.example.volunteer_platform.shared_dto.EventRegistrationDTO;
import com.example.volunteer_platform.shared_dto.UserLoginDTO;
import com.example.volunteer_platform.shared_dto.UserRegistrationDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.example.volunteer_platform.client.utils.RequestBuilderUtils.validateNonNull;

public class AccountRequestBuilder {

    public static HttpEntity<UserRegistrationDTO> createRegistrationRequest(String email, String password, String username) {
        validateNonNull(email, password, username);

        UserRegistrationDTO registrationRequest = new UserRegistrationDTO(email, password, username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(registrationRequest, headers);
    }

    public static HttpEntity<UserLoginDTO> createLoginRequest(String email, String password) {
        validateNonNull(email, password);

        UserLoginDTO loginRequest = new UserLoginDTO(email, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(loginRequest, headers);
    }
}
