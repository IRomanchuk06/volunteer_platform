package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.server.model.Message;
import com.example.volunteer_platform.server.model.Notification;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface MessageMapper {
    @Mapping(target = "id", expression = "java(message.getId())")
    @Mapping(target = "type", expression = "java(message.getType().name())")
    @Mapping(target = "senderEmail", expression = "java(message.getSender().getEmail())")
    @Mapping(target = "recipientEmail", expression = "java(message.getRecipient().getEmail())")
    @Mapping(target = "message", expression = "java(message.getMessage())")
    @Mapping(target = "createdAt", expression = "java(message.getCreatedAt())")
    @Mapping(target = "read", expression = "java(message.isRead())")
    MessageResponseDTO toMessageResponseDTO(Message message);

    default List<MessageResponseDTO> toMessageResponseDTOList(List<Notification> notifications) {
        if (notifications == null) {
            return Collections.emptyList();
        }
        List<Message> messages = notifications.stream()
                .filter(Message.class::isInstance)
                .map(Message.class::cast)
                .toList();
        return messages.stream().map(this::toMessageResponseDTO).collect(Collectors.toList());
    }
}
