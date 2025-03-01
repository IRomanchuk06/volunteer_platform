package com.example.volunteer_platform.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public abstract class Notification {

    public enum NotificationType {
        MESSAGE,
        VOLUNTEER_RESPONSE,
        SYSTEM_ALERT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private LocalDateTime createdAt;
    private boolean isRead;
}

