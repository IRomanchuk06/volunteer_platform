package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exceptions.EventAlreadyExistsException;
import com.example.volunteer_platform.server.exceptions.EventNotExistsException;
import com.example.volunteer_platform.server.exceptions.EventVolunteerLimitException;
import com.example.volunteer_platform.server.mapper.EventMapper;
import com.example.volunteer_platform.server.model.*;
import com.example.volunteer_platform.server.repository.EventRepository;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ResponseService responseService;

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper, MessageService messageService, ResponseService responseService) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.responseService = responseService;
    }

    public EventResponseDTO createEvent(String name, String description, String location, LocalDate date,
                                        Optional<LocalTime> startTime, Optional<LocalTime> endTime, User currentUser,
                                        int numOfRequiredVolunteers) {
        boolean eventExists = eventRepository.existsByNameAndDate(name, date);
        if (eventExists) {
            throw new EventAlreadyExistsException("Event with this name already exists on the given date.");
        }

        LocalTime resolvedStartTime = startTime.orElse(null);
        LocalTime resolvedEndTime = endTime.orElse(null);

        Event event = Event.builder().name(name).description(description).location(location).date(date).startTime(
                resolvedStartTime).endTime(resolvedEndTime).customer((Customer) currentUser).numOfRequiredVolunteers(
                numOfRequiredVolunteers).build();

        eventRepository.save(event);

        return eventMapper.toResponseDTO(event);
    }

    public List<EventResponseDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(eventMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public EventResponseDTO responseToEvent(Long eventId, Volunteer volunteer) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotExistsException("Event not found"));

        if (event.getVolunteers().size() < event.getNumOfRequiredVolunteers()) {
            eventRepository.addVolunteerToEvent(eventId, volunteer.getId());
            eventRepository.save(event);

            responseService.responseToEvent(event, volunteer);

            return eventMapper.toResponseDTO(event);
        }
        throw new EventVolunteerLimitException("Volunteer limit reached");
    }
}
