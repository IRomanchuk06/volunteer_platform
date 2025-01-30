package com.example.volunteer_platform.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "events")
@Data
@Entity
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true, exclude = "events")
public class Volunteer extends User {

    @JsonBackReference
    @ManyToMany(mappedBy = "volunteers")
    private Set<Event> events = new HashSet<>();
}
