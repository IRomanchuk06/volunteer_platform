package com.example.volunteer_platform.server.unit.events;

import com.example.volunteer_platform.server.events.MessageSentEvent;
import com.example.volunteer_platform.server.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEvent;

import static org.junit.jupiter.api.Assertions.*;

class MessageSentEventTests {

    @Test
    void shouldCreateEventWithCorrectData() {
        Object source = new Object();
        Customer sender = new Customer();
        Customer recipient = new Customer();
        MessageSentEvent event = new MessageSentEvent(source, "Test", sender, recipient);

        assertAll(
                () -> assertEquals(source, event.getSource()),
                () -> assertEquals("Test", event.getMessage()),
                () -> assertSame(sender, event.getSender()),
                () -> assertSame(recipient, event.getRecipient())
        );
    }

    @Test
    void shouldBeApplicationEventSubclass() {
        MessageSentEvent event = new MessageSentEvent(this, "Msg", new Customer(), new Customer());
        assertInstanceOf(ApplicationEvent.class, event);
    }
}