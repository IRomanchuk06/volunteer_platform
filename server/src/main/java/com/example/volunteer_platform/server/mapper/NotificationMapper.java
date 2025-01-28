package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.shared_dto.NotificationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class,
        imports = Notification.NotificationType.class)
public interface NotificationMapper {
    @Mapping(target = "type", expression = "java(notification.getType().name())")
    NotificationResponseDTO toNotificationResponseDTO(Notification notification);
}