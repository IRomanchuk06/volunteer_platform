package com.example.volunteer_platform.client.config;

import com.example.volunteer_platform.client.utils.CookieHttpRequestInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTemplateConfig {
    public static RestTemplate createRestTemplateWithSessionId(String sessionId) {
        RestTemplate restTemplate = new RestTemplate();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        restTemplate.getMessageConverters().addFirst(converter);

        restTemplate.getInterceptors().add(new CookieHttpRequestInterceptor(sessionId));

        return restTemplate;
    }
}
