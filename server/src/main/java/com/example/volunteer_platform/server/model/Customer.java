package com.example.volunteer_platform.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "customers")
@SuperBuilder
@NoArgsConstructor
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    @Builder.Default
    private List<Event> events = new ArrayList<>();

}
