package com.example.volunteer_platform.shared_dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int numOfRequiredVolunteers;
    private int numOfRespondingVolunteers;
    private UserResponseDTO customer;
}
