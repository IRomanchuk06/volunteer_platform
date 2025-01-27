package com.example.volunteer_platform.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "volunteers")
@NoArgsConstructor
@SuperBuilder
public class Volunteer extends User {

    @ManyToMany(mappedBy = "volunteers")
    private Set<Event> events = new HashSet<>();

}