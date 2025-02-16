package com.example.volunteer_platform.server.service;

import com.example.volunteer_platform.server.exсeptions.EventAlreadyExistsException;
import com.example.volunteer_platform.server.exсeptions.EventNotExistsException;
import com.example.volunteer_platform.server.exсeptions.EventVolunteerLimitException;
import com.example.volunteer_platform.server.mapper.EventMapper;
import com.example.volunteer_platform.server.model.*;
import com.example.volunteer_platform.server.repository.EventRepository;
import com.example.volunteer_platform.shared_dto.EventResponseDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTests {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService;

    @Mock
    private ResponseService responseService;


    private Event event;
    private Volunteer volunteer;
    private Customer customer;
    private EventResponseDTO eventResponseDTO;

    @BeforeEach
    void setUp() {
        customer = Customer.builder().id(1L).username("customer").password("1234").email("cust@gmail.com").role(User.UserRole.CUSTOMER).build();
        volunteer = Volunteer.builder().id(2L).username("volunteer").password("1234").email("vol@gmail.com").role(User.UserRole.VOLUNTEER).build();

        event = Event.builder()
                .id(10L)
                .name("Test Event")
                .description("Description")
                .location("Location")
                .date(LocalDate.now())
                .customer(customer)
                .numOfRequiredVolunteers(2)
                .volunteers(new HashSet<>())
                .build();

        eventResponseDTO = new EventResponseDTO();
        eventResponseDTO.setId(10L);
        eventResponseDTO.setName("Test Event");
    }

    @Test
    void testResponseToEvent_Success() {
        when(eventRepository.findById(10L)).thenReturn(Optional.of(event));
        doNothing().when(eventRepository).addVolunteerToEvent(anyLong(), anyLong());
        when(eventMapper.toResponseDTO(any(Event.class))).thenReturn(eventResponseDTO);

        EventResponseDTO response = eventService.responseToEvent(10L, volunteer);

        assertNotNull(response);
        assertEquals(10L, response.getId());

        verify(eventRepository, times(1)).addVolunteerToEvent(anyLong(), anyLong());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testResponseToEvent_EventNotFound() {
        when(eventRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(EventNotExistsException.class, () -> eventService.responseToEvent(10L, volunteer));
    }

    @Test
    void testResponseToEvent_VolunteerLimitReached() {
        event.getVolunteers().add(volunteer);
        event.getVolunteers().add(Volunteer.builder().id(3L).build());

        when(eventRepository.findById(10L)).thenReturn(Optional.of(event));

        assertThrows(EventVolunteerLimitException.class, () -> eventService.responseToEvent(10L, volunteer));
    }

    @Test
    void testCreateEvent_Success() {
        when(eventRepository.existsByNameAndDate("Test Event", LocalDate.now())).thenReturn(false);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(eventMapper.toResponseDTO(any(Event.class))).thenReturn(eventResponseDTO);

        EventResponseDTO response = eventService.createEvent(
                "Test Event", "Description", "Location", LocalDate.now(),
                Optional.empty(), Optional.empty(), customer, 2
        );

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("Test Event", response.getName());
        verify(eventRepository, times(1)).save(any(Event.class));  // Проверяем, что save был вызван один раз
    }

    @Test
    void testCreateEvent_EventAlreadyExists() {
        when(eventRepository.existsByNameAndDate("Test Event", LocalDate.now())).thenReturn(true);

        assertThrows(EventAlreadyExistsException.class, () ->
                eventService.createEvent(
                        "Test Event", "Description", "Location", LocalDate.now(),
                        Optional.empty(), Optional.empty(), customer, 2
                )
        );
    }

    @Test
    void testGetAllEvents_Success() {
        Event event1 = Event.builder().id(1L).name("Event 1").description("Description 1").location("Location 1")
                .date(LocalDate.now()).numOfRequiredVolunteers(3).build();
        Event event2 = Event.builder().id(2L).name("Event 2").description("Description 2").location("Location 2")
                .date(LocalDate.now()).numOfRequiredVolunteers(2).build();
        List<Event> events = List.of(event1, event2);

        when(eventRepository.findAll()).thenReturn(events);
        when(eventMapper.toResponseDTO(event1)).thenReturn(new EventResponseDTO(1L, "Event 1", "Description 1", "Location 1", LocalDate.now(), null, null, 3, 0, null, null));
        when(eventMapper.toResponseDTO(event2)).thenReturn(new EventResponseDTO(2L, "Event 2", "Description 2", "Location 2", LocalDate.now(), null, null, 2, 0, null, null));

        List<EventResponseDTO> response = eventService.getAllEvents();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Event 1", response.get(0).getName());
        assertEquals("Event 2", response.get(1).getName());

        verify(eventRepository, times(1)).findAll();
    }


}
