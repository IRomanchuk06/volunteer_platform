package com.example.volunteer_platform.server.events;

import com.example.volunteer_platform.server.model.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MessageSentEvent extends ApplicationEvent {
    private final String message;
    private final User sender;
    private final User recipient;

    public MessageSentEvent(Object source, String message, User sender, User recipient) {
        super(source);
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }
}
