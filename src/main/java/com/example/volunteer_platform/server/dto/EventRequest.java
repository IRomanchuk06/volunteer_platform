package com.example.volunteer_platform.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class EventRequest {
    private String name;
    private String description;
    private String location;
    private String date;
    private String startTime;
    private String endTime;
}
