package com.example.volunteer_platform.client.utils;

import static com.example.volunteer_platform.client.constants.UtilsConstants.*;

public class RequestBuilderUtils {

    public static void validateNonNull(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException(NULL_PARAMETER_ERROR);
            }
        }
    }
}
