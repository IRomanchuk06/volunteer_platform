package com.example.volunteer_platform.server.unit.service;

import com.example.volunteer_platform.server.mapper.MessageMapper;
import com.example.volunteer_platform.server.mapper.VolunteerResponseMapper;
import com.example.volunteer_platform.server.model.Message;
import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.server.repository.NotificationRepository;
import com.example.volunteer_platform.server.service.NotificationService;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerEventResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTests {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private VolunteerResponseMapper volunteerResponseMapper;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void testGetVolunteerResponses_Success() {
        Volunteer recipient = new Volunteer();
        Message notification1 = new Message();
        Message notification2 = new Message();
        List<Notification> notifications = List.of(notification1, notification2);

        when(notificationRepository.findByRecipientAndType(recipient, Notification.NotificationType.VOLUNTEER_RESPONSE))
                .thenReturn(notifications);

        VolunteerEventResponseDTO dto1 = new VolunteerEventResponseDTO();
        VolunteerEventResponseDTO dto2 = new VolunteerEventResponseDTO();
        when(volunteerResponseMapper.toVolunteerResponseDTOList(notifications)).thenReturn(List.of(dto1, dto2));

        List<VolunteerEventResponseDTO> response = notificationService.getVolunteerResponses(recipient);

        assertNotNull(response);
        assertEquals(2, response.size());
        verify(notificationRepository, times(1))
                .findByRecipientAndType(recipient, Notification.NotificationType.VOLUNTEER_RESPONSE);
    }

    @Test
    void testGetReceivedMessages_Success() {
        User recipient = new Volunteer();
        Message message1 = new Message();
        Message message2 = new Message();
        List<Notification> notifications = List.of(message1, message2);

        when(notificationRepository.findByRecipientAndType(recipient, Notification.NotificationType.MESSAGE))
                .thenReturn(notifications);

        MessageResponseDTO dto1 = new MessageResponseDTO();
        MessageResponseDTO dto2 = new MessageResponseDTO();
        when(messageMapper.toMessageResponseDTOList(notifications)).thenReturn(List.of(dto1, dto2));

        List<MessageResponseDTO> response = notificationService.getReceivedMessages(recipient);

        assertNotNull(response);
        assertEquals(2, response.size());
        verify(notificationRepository, times(1))
                .findByRecipientAndType(recipient, Notification.NotificationType.MESSAGE);
    }
}
