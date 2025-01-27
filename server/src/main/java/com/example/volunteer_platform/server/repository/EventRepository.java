package com.example.volunteer_platform.server.repository;

import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByNameAndDate(String name, LocalDate date);

    @Modifying
    @Query("UPDATE Event e SET e.numOfRespondingVolunteers = e.numOfRespondingVolunteers + 1 WHERE e.id = :eventId AND e.numOfRespondingVolunteers < e.numOfRequiredVolunteers")
    int incrementRespondingVolunteers(@Param("eventId") Long eventId);

    @Modifying
    @Query(
            value = "INSERT INTO event_volunteers (event_id, user_id) VALUES (:eventId, :volunteerId)",
            nativeQuery = true
    )
    void addVolunteerToEvent(
            @Param("eventId") Long eventId,
            @Param("volunteerId") Long volunteerId
    );
}
