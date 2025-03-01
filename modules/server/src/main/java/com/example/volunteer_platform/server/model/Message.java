package com.example.volunteer_platform.server.model;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Message extends Notification{
    @EqualsAndHashCode.Include
    @ToString.Include
    private String message;
}
