package com.example.volunteer_platform.server.repository;

import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByNameAndDate(String name, LocalDate date);

    @Query("SELECT COUNT(v) FROM Event e JOIN e.volunteers v WHERE e.id = :eventId")
    int getRespondingVolunteersCount(@Param("eventId") Long eventId);

    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO event_volunteers (event_id, volunteer_id) VALUES (:eventId, :volunteerId)",
            nativeQuery = true
    )
    void addVolunteerToEvent(
            @Param("eventId") Long eventId,
            @Param("volunteerId") Long volunteerId
    );
}
