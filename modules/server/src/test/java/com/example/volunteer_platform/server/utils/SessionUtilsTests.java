package com.example.volunteer_platform.server.utils;

import com.example.volunteer_platform.server.exceptions.SessionNotFoundException;
import com.example.volunteer_platform.server.exceptions.UserNotFoundInSessionException;
import com.example.volunteer_platform.server.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionUtilsTests {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private final User testUser = new User() {{
        setId(1L);
        setUsername("testUser");
    }};

    @Test
    void shouldLoadSessionUtilsClass() {
        SessionUtils sessionUtils = new SessionUtils();
        assertNotNull(sessionUtils, "SessionUtils class should be loadable");
    }

    @Test
    void shouldReturnUserWhenSessionAndUserExist() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(testUser);

        User result = SessionUtils.getUserFromSession(request);

        assertEquals(testUser, result);
        verify(request).getSession(false);
        verify(session).getAttribute("currentUser");
    }

    @Test
    void shouldThrowSessionNotFoundExceptionWhenNoSession() {
        when(request.getSession(false)).thenReturn(null);

        SessionNotFoundException exception = assertThrows(
                SessionNotFoundException.class,
                () -> SessionUtils.getUserFromSession(request)
        );

        assertEquals("No active session found", exception.getMessage());
        verify(request).getSession(false);
        verifyNoInteractions(session);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenSessionExistsButNoUser() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);

        UserNotFoundInSessionException exception = assertThrows(
                UserNotFoundInSessionException.class,
                () -> SessionUtils.getUserFromSession(request)
        );

        assertEquals("No user found in the current session", exception.getMessage());
        verify(request).getSession(false);
        verify(session).getAttribute("currentUser");
    }
}