package com.example.volunteer_platform.client.utils;

import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.NotificationResponseDTO;

import static com.example.volunteer_platform.client.constants.UtilsConstants.*;

public class DisplayFormatter {

    public static String formatEventForDisplay(EventResponseDTO event) {
        if (event == null) {
            return EVENT_NOT_AVAILABLE;
        }

        StringBuilder builder = new StringBuilder();

        builder.append(EVENT_DETAILS_HEADER);
        builder.append(NAME_LABEL).append(event.getName()).append(NEW_LINE);
        builder.append(DESCRIPTION_LABEL).append(event.getDescription()).append(NEW_LINE);
        builder.append(LOCATION_LABEL).append(event.getLocation()).append(NEW_LINE);
        builder.append(DATE_LABEL).append(event.getDate()).append(NEW_LINE);

        String time = (event.getStartTime() != null ? event.getStartTime() : NOT_AVAILABLE) + DASH
                + (event.getEndTime() != null ? event.getEndTime() : NOT_AVAILABLE);
        builder.append(TIME_LABEL).append(time).append(NEW_LINE);

        builder.append(CUSTOMER_LABEL).append(event.getCustomer() != null ? event.getCustomer().getUsername() : NOT_AVAILABLE).append(NEW_LINE);
        builder.append(EVENT_VOLUNTEER_TEAM);
        if(event.getNumOfRequiredVolunteers() != 0) {
            builder.append(event.getNumOfRespondingVolunteers()).append(BACKSLASH).append(event.getNumOfRequiredVolunteers());
        } else builder.append(NOT_AVAILABLE);
        builder.append(NEW_LINE);
        builder.append(ID_LABEL).append(event.getId());

        return builder.toString();
    }

    public static String formatNotificationForDisplay(NotificationResponseDTO notification) {
        if (notification == null) {
            return NOTIFICATION_NOT_AVAILABLE;
        }

        StringBuilder builder = new StringBuilder();

        builder.append(NOTIFICATION_HEADER);

        builder.append(ID_LABEL).append(notification.getId()).append(NEW_LINE);
        builder.append(TYPE_LABEL).append(notification.getType()).append(NEW_LINE);

        builder.append(SENDER_LABEL)
                .append(notification.getSender() != null ?
                        notification.getSender().getUsername() : NOT_AVAILABLE)
                .append(NEW_LINE);

        builder.append(RECIPIENT_LABEL)
                .append(notification.getRecipient() != null ?
                        notification.getRecipient().getUsername() : NOT_AVAILABLE)
                .append(NEW_LINE);

        builder.append(DATE_LABEL)
                .append(notification.getCreatedAt() != null ?
                        notification.getCreatedAt() : NOT_AVAILABLE)
                .append(NEW_LINE);

        builder.append(STATUS_LABEL)
                .append(notification.isRead() ? "Yes" : "No")
                .append(NEW_LINE);

        builder.append(MESSAGE_HEADER)
                .append(notification.getMessage() != null ?
                        notification.getMessage() : "No message content")
                .append(NEW_LINE);

        return builder.toString();
    }
}
