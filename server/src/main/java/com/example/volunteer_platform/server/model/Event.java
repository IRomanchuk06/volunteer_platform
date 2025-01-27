package com.example.volunteer_platform.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @PositiveOrZero(message = "Number of responding volunteers must be positive or zero")
    private int numOfRespondingVolunteers;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "event_volunteers",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Volunteer> volunteers = new HashSet<>();
}
