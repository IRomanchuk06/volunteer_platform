package com.example.volunteer_platform.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Nullable
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @Nullable
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    private int numOfRequiredVolunteers;
    private int numOfRespondingVolunteers;
}
