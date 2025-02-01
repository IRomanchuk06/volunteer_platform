package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.shared_dto.NotificationResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface NotificationMapper {
    @Mapping(target = "type", expression = "java(notification.getType().name())")
    @Mapping(target = "senderEmail", expression = "java(notification.getSender().getEmail())")
    @Mapping(target = "recipientEmail", expression = "java(notification.getRecipient().getEmail())")
    NotificationResponseDTO toNotificationResponseDTO(Notification notification);

    default List<NotificationResponseDTO> toNotificationResponseDTOList(List<Notification> notifications) {
        if (notifications == null) {
            return Collections.emptyList();
        }
        return notifications.stream().map(this::toNotificationResponseDTO).collect(Collectors.toList());
    }

    default List<VolunteerResponseDTO> toVolunteerResponseDTOList(List<Notification> notifications) {
        if (notifications == null) {
            return Collections.emptyList();
        }

        return notifications.stream()
                .map(notification -> {
                    VolunteerResponseDTO dto = new VolunteerResponseDTO();

                    dto.setId(notification.getId());
                    dto.setCreatedAt(notification.getCreatedAt());
                    dto.setMessage(notification.getMessage());

                    if (notification.getSender() != null) {
                        dto.setVolunteerId(notification.getSender().getId());
                        dto.setVolunteerName(notification.getSender().getUsername());
                    }

                    dto.setEventName(extractEventName(notification.getMessage()));
                    dto.setStatus(extractStatus(notification.getMessage()));

                    return dto;
                })
                .collect(Collectors.toList());
    }

    private String extractEventName(String message) {
        if (message == null) {
            return "Unnamed Event";
        }

        int start = message.indexOf("for ") + 4;
        int end = message.indexOf(":", start);
        return (start > 3 && end > start)
                ? message.substring(start, end).trim()
                : "Unnamed Event";
    }

    private String extractStatus(String message) {
        if (message == null) {
            return "PENDING";
        }

        if (message.contains("approved")) {
            return "APPROVED";
        } else if (message.contains("rejected")) {
            return "REJECTED";
        } else {
            return "PENDING";
        }
    }
}
