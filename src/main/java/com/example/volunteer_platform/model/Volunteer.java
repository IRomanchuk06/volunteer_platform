package com.example.volunteer_platform.model;

import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Entity;

@Entity
@Table(name = "volunteers")
@NoArgsConstructor
@SuperBuilder
public class Volunteer extends User {
}
