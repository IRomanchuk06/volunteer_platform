package com.example.volunteer_platform.service;

import com.example.volunteer_platform.model.Event;
import com.example.volunteer_platform.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(String name, String description, LocalDate date, LocalTime startTime, LocalTime endTime) {
        Event newEvent = new Event(name, description, "Unknown location", date, startTime, endTime);
        return eventRepository.save(newEvent);
    }
}
