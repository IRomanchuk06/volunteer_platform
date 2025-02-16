package com.example.volunteer_platform.server.utils;

import com.example.volunteer_platform.server.exсeptions.SessionNotFoundException;
import com.example.volunteer_platform.server.exсeptions.UserNotFoundInSessionException;
import com.example.volunteer_platform.server.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    public static User getUserFromSession(HttpServletRequest request) throws SessionNotFoundException, UserNotFoundInSessionException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new SessionNotFoundException("No active session found");
        }

        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            throw new UserNotFoundInSessionException("No user found in the current session");
        }

        return user;
    }
}
