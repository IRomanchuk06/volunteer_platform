package com.example.volunteer_platform.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@ToString(exclude = "event")
@EqualsAndHashCode(exclude = "event", callSuper = true)
public class Response extends Notification{
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
