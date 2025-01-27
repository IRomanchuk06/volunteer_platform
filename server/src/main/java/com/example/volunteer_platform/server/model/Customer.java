package com.example.volunteer_platform.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.*;

@Entity
@Table(name = "customers")
@SuperBuilder
@NoArgsConstructor
public class Customer extends User {

    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    @Builder.Default
    private Set<Event> events = new HashSet<>();

}
