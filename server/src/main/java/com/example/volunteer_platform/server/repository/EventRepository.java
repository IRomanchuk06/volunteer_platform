package com.example.volunteer_platform.server.repository;

import com.example.volunteer_platform.server.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByNameAndDate(String name, LocalDate date);
}
