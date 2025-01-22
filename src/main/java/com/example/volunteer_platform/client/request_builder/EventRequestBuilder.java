package com.example.volunteer_platform.client.request_builder;

import com.example.volunteer_platform.client.dto.AddEventRequest;
import org.springframework.http.HttpEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.example.volunteer_platform.client.utils.RequestBuilderUtils.createJsonRequestEntity;
import static com.example.volunteer_platform.client.utils.RequestBuilderUtils.validateNonNull;

public class EventRequestBuilder {
    public static HttpEntity<String> createAddEventRequest(String name, String description, String location,
                                                           LocalDate date, LocalTime startTime, LocalTime endTime) {
        validateNonNull(name, description, location, date);

        AddEventRequest eventRequest = new AddEventRequest(name, description, location, date, startTime, endTime);
        return createJsonRequestEntity(eventRequest);
    }
}
