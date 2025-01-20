package com.example.volunteer_platform.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "volunteers")
@NoArgsConstructor
@SuperBuilder
public class Volunteer extends User {
}