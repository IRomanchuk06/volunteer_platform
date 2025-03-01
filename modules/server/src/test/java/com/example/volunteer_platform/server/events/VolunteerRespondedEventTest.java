package com.example.volunteer_platform.server.events;

import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.Event;
import com.example.volunteer_platform.server.model.Volunteer;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEvent;
import static org.junit.jupiter.api.Assertions.*;

class VolunteerRespondedEventTest {
    @Test
    void shouldInitializeEventWithAllParameters() {
        Event testEvent = new Event();
        Volunteer volunteer = new Volunteer();
        Customer customer = new Customer();
        VolunteerRespondedEvent event = new VolunteerRespondedEvent(this, testEvent, volunteer, customer);

        assertAll(
                () -> assertSame(testEvent, event.getEvent()),
                () -> assertSame(volunteer, event.getVolunteer()),
                () -> assertSame(customer, event.getCustomer())
        );
    }

    @Test
    void shouldInheritFromApplicationEvent() {
        VolunteerRespondedEvent event = new VolunteerRespondedEvent(this, new Event(), new Volunteer(), new Customer());
        assertTrue(event instanceof ApplicationEvent);
    }
}
