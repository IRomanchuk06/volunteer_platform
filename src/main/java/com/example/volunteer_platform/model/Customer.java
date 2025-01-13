package com.example.volunteer_platform.model;

import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Entity;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@SuperBuilder
public class Customer extends User {
}
