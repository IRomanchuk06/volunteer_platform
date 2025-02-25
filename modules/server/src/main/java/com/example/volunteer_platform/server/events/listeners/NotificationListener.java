package com.example.volunteer_platform.server.events.listeners;

import com.example.volunteer_platform.server.events.MessageSentEvent;
import com.example.volunteer_platform.server.events.VolunteerRespondedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    // ADD ACTIONS!!

    @EventListener
    public void handleMessageSentEvent(MessageSentEvent event) {
        System.out.println("handleMessageSentEvent");
    }

    @EventListener
    public void handleVolunteerRespondedEvent(VolunteerRespondedEvent event) {
        System.out.println("handleVolunteerRespondedEvent");
    }

    // ADD ACTIONS!!
}
