package com.example.volunteer_platform.shared_dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventDTO {

    private Long id;

    private String name;
    private String description;
    private String location;

    private LocalDate date;

    private LocalTime startTime;
    private LocalTime endTime;

    private int numOfRequiredVolunteers;
    private int numOfRespondingVolunteers;

    private CustomerDTO customer;
}