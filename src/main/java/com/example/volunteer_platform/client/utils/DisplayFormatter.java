package com.example.volunteer_platform.client.utils;

import com.example.volunteer_platform.server.model.Event;

import static com.example.volunteer_platform.client.constants.UtilsConstants.*;

public class DisplayFormatter {

    public static String formatEventForDisplay(Event event) {
        if (event == null) {
            return EVENT_NOT_AVAILABLE;
        }

        StringBuilder builder = new StringBuilder();

        builder.append(EVENT_DETAILS_HEADER);
        builder.append(NAME_LABEL).append(event.getName()).append(NEW_LINE);
        builder.append(DESCRIPTION_LABEL).append(event.getDescription()).append(NEW_LINE);
        builder.append(LOCATION_LABEL).append(event.getLocation()).append(NEW_LINE);
        builder.append(DATE_LABEL).append(event.getDate()).append(NEW_LINE);

        String time = (event.getStartTime() != null ? event.getStartTime() : NOT_AVAILABLE) + " - "
                + (event.getEndTime() != null ? event.getEndTime() : NOT_AVAILABLE);
        builder.append(TIME_LABEL).append(time).append(NEW_LINE);

        builder.append(CUSTOMER_LABEL).append(event.getCustomer() != null ? event.getCustomer().getUsername() : NOT_AVAILABLE).append(NEW_LINE);
        builder.append(ID_LABEL).append(event.getId());

        return builder.toString();
    }
}
