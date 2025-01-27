package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exeptions.EventAlreadyExistsException;
import com.example.volunteer_platform.server.exeptions.EventNotExistsException;
import com.example.volunteer_platform.server.exeptions.EventVolunteerLimitException;
import com.example.volunteer_platform.server.exeptions.VolunteerAlreadyParticipatingException;
import com.example.volunteer_platform.server.mapper.EventMapper;
import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.repository.EventRepository;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
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

        System.out.println(events);

        return events.stream()
                .map(eventMapper::toResponseDTO)
                .toList();
    }

    public EventResponseDTO responseToEvent(Long eventId, User user) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotExistsException("The event with this id does not exist"));

        int eventResponding = event.getNumOfRespondingVolunteers();
        int eventRequired = event.getNumOfRequiredVolunteers();
        if(eventRequired < eventResponding) {
            throw new EventVolunteerLimitException("Volunteers for the event have already been recruited");
        } else if (event.getVolunteers().contains(user)) {
            throw new VolunteerAlreadyParticipatingException("A volunteer is already participating in the event.");
        }

        event.setNumOfRespondingVolunteers(eventResponding + 1);
        event.getVolunteers().add(user);

        eventRepository.save(event);

        return eventMapper.toResponseDTO(event);
    }
}
