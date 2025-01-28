package com.example.volunteer_platform.server.repository;

import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findBySender(User sender);
    List<Notification> findByRecipient(User recipient);

    @Query("SELECT n FROM Notification n WHERE n.recipient = :recipient AND n.type = :type")
    List<Notification> findByRecipientAndType(
            @Param("recipient") User recipient,
            @Param("type") Notification.NotificationType type
    );
}
