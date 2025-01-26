package com.example.volunteer_platform.shared_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistrationDTO {
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Location cannot be null")
    private String location;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    private LocalTime startTime;
    private LocalTime endTime;

    @PositiveOrZero(message = "Number of required volunteers must be positive or zero")
    private int numOfRequiredVolunteers;
}
