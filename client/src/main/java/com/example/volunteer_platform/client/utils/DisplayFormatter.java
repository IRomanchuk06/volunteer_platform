package com.example.volunteer_platform.client.utils;

import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.NotificationResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerResponseDTO;

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
                .append(notification.getSenderEmail() != null ?
                        notification.getSenderEmail() : NOT_AVAILABLE)
                .append(NEW_LINE);

        builder.append(RECIPIENT_LABEL)
                .append(notification.getRecipientEmail() != null ?
                        notification.getRecipientEmail() : NOT_AVAILABLE)
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

    public static String formatVolunteerResponseForDisplay(VolunteerResponseDTO response) {
        if (response == null) {
            return VOLUNTEER_RESPONSE_NOT_AVAILABLE;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(VOLUNTEER_RESPONSE_HEADER);

        builder.append(ID_LABEL).append(response.getId()).append(NEW_LINE);

        builder.append(VOLUNTEER_LABEL)
                .append(response.getVolunteerName() != null ?
                        response.getVolunteerName() + " (ID: " + response.getVolunteerId() + ")" :
                        NOT_AVAILABLE)
                .append(NEW_LINE);

        builder.append(EVENT_LABEL)
                .append(response.getEventName() != null ?
                        response.getEventName() : NOT_AVAILABLE)
                .append(NEW_LINE);

        builder.append(STATUS_LABEL)
                .append(response.getStatus() != null ?
                        response.getStatus().toUpperCase() : NOT_AVAILABLE)
                .append(NEW_LINE);

        builder.append(RESPONSE_TIME_LABEL)
                .append(response.getCreatedAt() != null ?
                        response.getCreatedAt() : NOT_AVAILABLE)
                .append(NEW_LINE);

        builder.append(MESSAGE_HEADER)
                .append(response.getMessage() != null ?
                        response.getMessage() : "No message content")
                .append(NEW_LINE);

        return builder.toString();
    }
}
