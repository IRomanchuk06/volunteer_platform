package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.shared_dto.NotificationResponseDTO;
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
}
