package com.example.volunteer_platform.model;

import javax.persistence.Entity;

@Entity
public class Volunteer extends User {

    public Volunteer() {
        super();
    }

    public Volunteer(String email, String password, String username) {
        super(email, password, username);
    }
}
