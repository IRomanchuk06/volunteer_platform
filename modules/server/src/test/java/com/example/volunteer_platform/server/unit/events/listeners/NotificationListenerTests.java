package com.example.volunteer_platform.server.unit.events.listeners;

import com.example.volunteer_platform.server.events.MessageSentEvent;
import com.example.volunteer_platform.server.events.VolunteerRespondedEvent;
import com.example.volunteer_platform.server.events.listeners.NotificationListener;
import com.example.volunteer_platform.server.unit.BaseUnitTests;
import org.hibernate.mapping.Component;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationListenerTests extends BaseUnitTests {

    @Spy
    @InjectMocks
    private NotificationListener notificationListener;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    void shouldHandleMessageSentEvent() {
        MessageSentEvent event = new MessageSentEvent(this, "Test", null, null);

        notificationListener.handleMessageSentEvent(event);

        verify(notificationListener, times(1)).handleMessageSentEvent(event);
    }

    @Test
    void shouldHandleVolunteerRespondedEvent() {
        VolunteerRespondedEvent event = new VolunteerRespondedEvent(this, null, null, null);

        notificationListener.handleVolunteerRespondedEvent(event);

        verify(notificationListener, times(1)).handleVolunteerRespondedEvent(event);
    }
}