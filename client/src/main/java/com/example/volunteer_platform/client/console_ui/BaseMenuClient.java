package com.example.volunteer_platform.client.console_ui;

import com.example.volunteer_platform.client.config.RestTemplateConfig;
import com.example.volunteer_platform.client.enums.AccountType;
import com.example.volunteer_platform.client.logging.AppLogger;
import com.example.volunteer_platform.shared_dto.UserLoginDTO;
import com.example.volunteer_platform.shared_dto.UserRegistrationDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.example.volunteer_platform.client.constants.ApiEndpoints.*;
import static com.example.volunteer_platform.client.constants.MenuConstants.*;
import static com.example.volunteer_platform.client.request_builder.AccountRequestBuilder.createLoginRequest;
import static com.example.volunteer_platform.client.request_builder.AccountRequestBuilder.createRegistrationRequest;
import static com.example.volunteer_platform.client.utils.ConsoleInputUtils.*;
import static com.example.volunteer_platform.client.utils.CookieUtils.getSessionIdFromResponse;

@Component
public class BaseMenuClient {

    private final VolunteerMenuClient volunteerMenuClient;
    private final CustomerMenuClient customerMenuClient;
    private final RestTemplate restTemplate;

    @Autowired
    public BaseMenuClient(VolunteerMenuClient volunteerMenuClient, CustomerMenuClient customerMenuClient) {
        this.volunteerMenuClient = volunteerMenuClient;
        this.customerMenuClient = customerMenuClient;
        this.restTemplate = new RestTemplate();
    }

    public void start() {
        while (true) {
            showMenu();
            int choice = getUserChoice();

            try {
                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        loginAccount();
                        break;
                    case 3:
                        logoutAccount();
                        return;
                    default:
                        AppLogger.CLIENT_LOGGER.warn(INVALID_CHOICE);
                }
            } catch (NumberFormatException e) {
                AppLogger.CLIENT_LOGGER.error(INVALID_CHOICE, e);
            }
        }
    }

    private void logoutAccount() {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + LOGOUT_URL, HttpMethod.POST,
                HttpEntity.EMPTY, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            AppLogger.CLIENT_LOGGER.info(EXIT_MESSAGE);
        } else {
            AppLogger.CLIENT_LOGGER.error(EXIT_ERROR);
        }
    }

    private void showMenu() {
        System.out.println("ff");
        AppLogger.CLIENT_LOGGER.info(MAIN_MENU_TITLE);
        AppLogger.CLIENT_LOGGER.info(MAIN_MENU_OPTIONS);
    }

    private void createAccount() {
        AppLogger.CLIENT_LOGGER.info(SELECT_ACCOUNT_TYPE);
        AppLogger.CLIENT_LOGGER.info(ACCOUNT_TYPE_OPTIONS);

        AccountType accountType = AccountType.fromValue(getUserChoice());
        String email = getRegistrationEmail(BASE_URL);

        if (email == null) {
            AppLogger.CLIENT_LOGGER.warn(EXIT_OPERATION_MESSAGE);
            return;
        }

        AppLogger.CLIENT_LOGGER.info(ENTER_PASSWORD_PROMPT);
        String password = getUserInputString();

        AppLogger.CLIENT_LOGGER.info(ENTER_USERNAME_PROMPT);
        String username = getUserInputString();

        HttpEntity<UserRegistrationDTO> requestEntity = createRegistrationRequest(email, password, username);
        String accountTypeUrl = accountType == AccountType.VOLUNTEER ? VOLUNTEERS_URL + CREATE_URL : CUSTOMERS_URL + CREATE_URL;

        try {
            ResponseEntity<UserResponseDTO> response = restTemplate.exchange(BASE_URL + accountTypeUrl, HttpMethod.POST, requestEntity,
                    UserResponseDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                AppLogger.CLIENT_LOGGER.info(ACCOUNT_CREATION_SUCCESS);
            } else {
                AppLogger.CLIENT_LOGGER.warn(ACCOUNT_CREATION_FAILED + response.getStatusCode());
                AppLogger.CLIENT_LOGGER.warn(RESPONSE_BODY + response.getBody());
            }
        } catch (Exception e) {
            AppLogger.CLIENT_LOGGER.error(ACCOUNT_CREATION_ERROR, e);
        }
    }

    private void loginAccount() {
        String email = getValidEmail(BASE_URL);

        if (email == null) {
            AppLogger.CLIENT_LOGGER.warn(EXIT_OPERATION_MESSAGE);
            return;
        }

        AppLogger.CLIENT_LOGGER.info(ENTER_PASSWORD_PROMPT);
        String password = getUserInputString();
        HttpEntity<UserLoginDTO> requestEntity = createLoginRequest(email, password);

        try {
            ResponseEntity<UserResponseDTO> response = restTemplate.exchange(BASE_URL + LOGIN_URL, HttpMethod.POST,
                    requestEntity, UserResponseDTO.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                AppLogger.CLIENT_LOGGER.warn(LOGIN_FAILED + "{}", response.getStatusCode());
                return;
            }

            AppLogger.CLIENT_LOGGER.info(LOGIN_SUCCESS);
            String sessionId = getSessionIdFromResponse(response);
            if (sessionId == null) {
                AppLogger.CLIENT_LOGGER.warn(FAILED_GET_COOKIES);
                return;
            }
            RestTemplate authenticatedRestTemplate = RestTemplateConfig.createRestTemplateWithSessionId(sessionId);
            UserResponseDTO responseBody = response.getBody();

            if (responseBody == null) {
                throw new IllegalStateException(NULL_RESPONSE_BODY);
            }

            String accountType = responseBody.getRole();
            if (VOLUNTEER.equals(accountType)) {
                volunteerMenuClient.start(authenticatedRestTemplate);
            } else if (CUSTOMER.equals(accountType)) {
                customerMenuClient.start(authenticatedRestTemplate);
            } else {
                throw new IllegalArgumentException(INVALID_ACCOUNT_TYPE + accountType);
            }

        } catch (Exception e) {
            AppLogger.CLIENT_LOGGER.error(LOGIN_ERROR, e);
        }
    }
}