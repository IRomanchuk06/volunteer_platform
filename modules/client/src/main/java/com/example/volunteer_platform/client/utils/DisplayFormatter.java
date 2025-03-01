package com.example.volunteer_platform.client.utils;

import com.example.volunteer_platform.shared_dto.EventResponseDTO;
import com.example.volunteer_platform.shared_dto.MessageResponseDTO;
import com.example.volunteer_platform.shared_dto.VolunteerEventResponseDTO;

import java.time.format.DateTimeFormatter;

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
        builder.append(DATE_LABEL).append(event.getDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT))).append(
                NEW_LINE);

        String time = (event.getStartTime() != null ? event.getStartTime().format(DateTimeFormatter.ofPattern(
                TIME_FORMAT)) : NOT_AVAILABLE) + DASH + (event.getEndTime() != null ? event.getEndTime().format(
                DateTimeFormatter.ofPattern(TIME_FORMAT)) : NOT_AVAILABLE);
        builder.append(TIME_LABEL).append(time).append(NEW_LINE);

        builder.append(CUSTOMER_LABEL).append(
                event.getCustomer() != null ? event.getCustomer().getUsername() : NOT_AVAILABLE).append(NEW_LINE);
        builder.append(EVENT_VOLUNTEER_TEAM);
        if (event.getNumOfRequiredVolunteers() != 0) {
            builder.append(event.getNumOfRespondingVolunteers()).append(BACKSLASH).append(
                    event.getNumOfRequiredVolunteers());
        } else builder.append(NOT_AVAILABLE);
        builder.append(NEW_LINE);
        builder.append(ID_LABEL).append(event.getId());

        return builder.toString();
    }

    public static String formatNotificationForDisplay(MessageResponseDTO message) {
        if (message == null) {
            return MESSAGE_NOT_AVAILABLE;
        }

        String builder = NOTIFICATION_HEADER +
                ID_LABEL + message.getId() + NEW_LINE +
                SENDER_LABEL +
                (message.getSenderEmail() != null ? message.getSenderEmail() : NOT_AVAILABLE) + NEW_LINE +
                RECIPIENT_LABEL +
                (message.getRecipientEmail() != null ? message.getRecipientEmail() : NOT_AVAILABLE) + NEW_LINE +
                DATE_LABEL + (message.getCreatedAt() != null ? message.getCreatedAt().format(
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)) : NOT_AVAILABLE) +
                NEW_LINE +
                STATUS_LABEL + (message.isRead() ? "Yes" : "No") + NEW_LINE +
                MESSAGE_HEADER +
                (message.getMessage() != null ? message.getMessage() : "No message content") + NEW_LINE;

        return builder;
    }

    public static String formatVolunteerResponseForDisplay(VolunteerEventResponseDTO response) {
        if (response == null) {
            return VOLUNTEER_RESPONSE_NOT_AVAILABLE;
        }

        String builder = VOLUNTEER_RESPONSE_HEADER +
                ID_LABEL + response.getId() + NEW_LINE +
                VOLUNTEER_LABEL +
                (response.getVolunteerName() != null ? response.getVolunteerName() + " (ID: " + response.getVolunteerId() + ")" : NOT_AVAILABLE) +
                NEW_LINE +
                EVENT_LABEL +
                (response.getEventName() != null ? response.getEventName() : NOT_AVAILABLE) + NEW_LINE +
                STATUS_LABEL + (response.isRead() ? "Yes" : "No") + NEW_LINE +
                RESPONSE_TIME_LABEL + (response.getCreatedAt() != null ? response.getCreatedAt().format(
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)) : NOT_AVAILABLE) +
                NEW_LINE;

        return builder;
    }
}
