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

    @Autowired
    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        List<EventResponseDTO> eventsResponses = eventService.getAllEvents();

        System.out.println(eventsResponses);

        return ResponseEntity.ok(eventsResponses);
    }
}
