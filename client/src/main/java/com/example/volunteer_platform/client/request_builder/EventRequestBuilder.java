package com.example.volunteer_platform.client.request_builder;

import com.example.volunteer_platform.shared_dto.EventRegistrationDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.example.volunteer_platform.client.utils.RequestBuilderUtils.validateNonNull;

public class EventRequestBuilder {
    public static HttpEntity<EventRegistrationDTO> createAddEventRequest(String name, String description, String location,
                                                                         LocalDate date, LocalTime startTime, LocalTime endTime,
                                                                         int numOfRequiredVolunteers) {
        validateNonNull(name, description, location, date);

        EventRegistrationDTO eventRequest = new EventRegistrationDTO(name, description, location, date, startTime, endTime,
                numOfRequiredVolunteers);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(eventRequest, headers);
    }
}
