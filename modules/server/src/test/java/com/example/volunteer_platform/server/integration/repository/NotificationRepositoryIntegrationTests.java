package com.example.volunteer_platform.server.integration.repository;

import com.example.volunteer_platform.server.integration.BaseIntegrationTests;
import com.example.volunteer_platform.server.model.Message;
import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.repository.NotificationRepository;
import com.example.volunteer_platform.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NotificationRepositoryIntegrationTests extends BaseIntegrationTests {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        notificationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testFindBySender() {
        User sender = new Volunteer();
        sender.setEmail("sender@example.com");
        sender.setUsername("sender");
        sender.setPassword("password");
        sender.setRole(User.UserRole.VOLUNTEER);
        sender = userRepository.save(sender);

        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(sender);
        message.setType(Notification.NotificationType.MESSAGE);
        message.setMessage("Test message");
        notificationRepository.save(message);

        List<Notification> notifications = notificationRepository.findBySender(sender);

        assertThat(notifications).hasSize(1);
        assertThat(notifications.getFirst().getSender()).isEqualTo(sender);
        assertThat(notifications.getFirst()).isInstanceOf(Message.class);
        assertThat(((Message) notifications.getFirst()).getMessage()).isEqualTo("Test message");
    }

    @Test
    void testFindByRecipient() {
        User recipient = new Volunteer();
        recipient.setEmail("recipient@example.com");
        recipient.setUsername("recipient");
        recipient.setPassword("password");
        recipient.setRole(User.UserRole.VOLUNTEER);
        recipient = userRepository.save(recipient);

        Message message = new Message();
        message.setSender(recipient);
        message.setRecipient(recipient);
        message.setType(Notification.NotificationType.MESSAGE);
        message.setMessage("Test message");
        notificationRepository.save(message);

        List<Notification> notifications = notificationRepository.findByRecipient(recipient);

        assertThat(notifications).hasSize(1);
        assertThat(notifications.getFirst().getRecipient()).isEqualTo(recipient);
        assertThat(notifications.getFirst()).isInstanceOf(Message.class);
        assertThat(((Message) notifications.getFirst()).getMessage()).isEqualTo("Test message");
    }

    @Test
    void testFindByRecipientAndType() {
        User recipient = new Volunteer();
        recipient.setEmail("recipient@example.com");
        recipient.setUsername("recipient");
        recipient.setPassword("password");
        recipient.setRole(User.UserRole.VOLUNTEER);
        recipient = userRepository.save(recipient);

        Message message1 = new Message();
        message1.setSender(recipient);
        message1.setRecipient(recipient);
        message1.setType(Notification.NotificationType.MESSAGE);
        message1.setMessage("Info message");
        notificationRepository.save(message1);

        Message message2 = new Message();
        message2.setSender(recipient);
        message2.setRecipient(recipient);
        message2.setType(Notification.NotificationType.VOLUNTEER_RESPONSE);
        message2.setMessage("Warning message");
        notificationRepository.save(message2);

        List<Notification> notifications = notificationRepository.findByRecipientAndType(
                recipient,
                Notification.NotificationType.MESSAGE
        );

        assertThat(notifications).hasSize(1);
        assertThat(notifications.getFirst().getType()).isEqualTo(Notification.NotificationType.MESSAGE);
        assertThat(notifications.getFirst()).isInstanceOf(Message.class);
        assertThat(((Message) notifications.getFirst()).getMessage()).isEqualTo("Info message");
    }
}