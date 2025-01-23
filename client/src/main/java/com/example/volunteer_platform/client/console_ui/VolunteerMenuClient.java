package com.example.volunteer_platform.client.console_ui;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class VolunteerMenuClient {
    private RestTemplate restTemplateWithCookies;

    public void start(RestTemplate restTemplateWithCookies) {
        this.restTemplateWithCookies = restTemplateWithCookies;
    }
}
