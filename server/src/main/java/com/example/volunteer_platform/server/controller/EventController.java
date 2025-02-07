package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.service.EventService;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.server.logging.AppLogger;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private static final Logger logger = AppLogger.SERVER_LOGGER;

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        logger.info("Incoming request to /events");

        List<EventResponseDTO> eventsResponses = eventService.getAllEvents();

        logger.info("Responding with status 200 and payload: {}", eventsResponses);

        return ResponseEntity.ok(eventsResponses);
    }
}
