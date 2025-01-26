package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.mapper.EventMapper;
import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.service.EventService;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @Autowired
    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();

        List<EventResponseDTO> eventResponses = events.stream()
                .map(eventMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(eventResponses);
    }
}
