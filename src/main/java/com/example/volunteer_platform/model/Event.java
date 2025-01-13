package com.example.volunteer_platform.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String location;

    private LocalDate date;
    private LocalTime startTime;

    @Nullable
    private LocalTime endTime;

    public Event(String name, String description, String location, LocalDate date, LocalTime startTime, @Nullable LocalTime endTime) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
