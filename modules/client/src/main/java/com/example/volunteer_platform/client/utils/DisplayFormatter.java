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

        StringBuilder builder = new StringBuilder();

        builder.append(NOTIFICATION_HEADER);

        builder.append(ID_LABEL).append(message.getId()).append(NEW_LINE);

        builder.append(SENDER_LABEL).append(
                message.getSenderEmail() != null ? message.getSenderEmail() : NOT_AVAILABLE).append(NEW_LINE);

        builder.append(RECIPIENT_LABEL).append(
                message.getRecipientEmail() != null ? message.getRecipientEmail() : NOT_AVAILABLE).append(NEW_LINE);

        builder.append(DATE_LABEL).append(message.getCreatedAt() != null ? message.getCreatedAt().format(
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)) : NOT_AVAILABLE).append(NEW_LINE);

        builder.append(STATUS_LABEL).append(message.isRead() ? "Yes" : "No").append(NEW_LINE);

        builder.append(MESSAGE_HEADER).append(
                message.getMessage() != null ? message.getMessage() : "No message content").append(NEW_LINE);

        return builder.toString();
    }

    public static String formatVolunteerResponseForDisplay(VolunteerEventResponseDTO response) {
        if (response == null) {
            return VOLUNTEER_RESPONSE_NOT_AVAILABLE;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(VOLUNTEER_RESPONSE_HEADER);

        builder.append(ID_LABEL).append(response.getId()).append(NEW_LINE);

        builder.append(VOLUNTEER_LABEL).append(
                response.getVolunteerName() != null ? response.getVolunteerName() + " (ID: " + response.getVolunteerId() + ")" : NOT_AVAILABLE).append(
                NEW_LINE);

        builder.append(EVENT_LABEL).append(
                response.getEventName() != null ? response.getEventName() : NOT_AVAILABLE).append(NEW_LINE);

        builder.append(STATUS_LABEL).append(response.isRead() ? "Yes" : "No").append(NEW_LINE);

        builder.append(RESPONSE_TIME_LABEL).append(response.getCreatedAt() != null ? response.getCreatedAt().format(
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)) : NOT_AVAILABLE).append(NEW_LINE);

        return builder.toString();
    }
}
