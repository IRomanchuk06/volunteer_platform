package com.example.volunteer_platform.client.utils;

import org.springframework.web.client.RestTemplate;

public class RestTemplateConfig {
    public static RestTemplate createRestTemplateWithSessionId(String sessionId) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new CookieHttpRequestInterceptor(sessionId));
        return restTemplate;
    }
}
