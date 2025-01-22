package com.example.volunteer_platform.client.utils;

import org.springframework.lang.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.io.IOException;

import static com.example.volunteer_platform.client.constants.UtilsConstants.SESSION_ID_COOKIE_NAME;

public class CookieHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final String sessionId;

    public CookieHttpRequestInterceptor(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add(HttpHeaders.COOKIE, SESSION_ID_COOKIE_NAME + sessionId);

        return execution.execute(request, body);
    }
}
