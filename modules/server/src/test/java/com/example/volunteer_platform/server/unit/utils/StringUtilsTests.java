package com.example.volunteer_platform.server.unit.utils;

import com.example.volunteer_platform.server.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StringUtilsTests {

    @Test
    void testStringUtilsClass() {
        StringUtils stringUtils = new StringUtils();
        assertNotNull(stringUtils, "StringUtils class should be loadable");
    }

    @ParameterizedTest
    @ValueSource(strings = {"test@example.com", "admin@domain.com", "user@test.org"})
    void testMaskEmail_NormalEmail(String email) {
        String expected = email.charAt(0) + "***" + email.substring(email.indexOf('@'));
        assertEquals(expected, StringUtils.maskEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a@b.c", "x@y.z"})
    void testMaskEmail_ShortEmail(String email) {
        assertEquals(email, StringUtils.maskEmail(email));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testMaskEmail_NullAndEmptyEmail(String email) {
        assertEquals("unknown", StringUtils.maskEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "noatsymbol"})
    void testMaskEmail_NoAtSymbol(String email) {
        assertEquals(email, StringUtils.maskEmail(email));
    }
}