package com.example.volunteer_platform.server.model;

import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Message extends Notification{
    private String message;
}
