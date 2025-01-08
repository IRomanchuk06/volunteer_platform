package com.example.volunteer_platform.model;

import javax.persistence.Entity;

@Entity
public class Customer extends User {
    public Customer() {
        super();
    }

    public Customer(String email, String password, String username) {
        super(email, password, username);
    }
}
