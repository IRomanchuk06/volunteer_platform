package com.example.volunteer_platform.server.events;

import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.model.Volunteer;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class VolunteerRespondedEvent extends ApplicationEvent {
    private final Event event;
    private final Volunteer volunteer;
    private final Customer customer;

    public VolunteerRespondedEvent(Object source, Event event, Volunteer volunteer, Customer customer) {
        super(source);
        this.event = event;
        this.volunteer = volunteer;
        this.customer = customer;
    }
}
