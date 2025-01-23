package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.dto.LoginRequest;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.volunteer_platform.server.utils.SessionUtils.getUserFromSession;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        User user = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        HttpSession session = request.getSession(true);
        session.setAttribute("currentUser", user);

        String sessionId = session.getId();
        response.addCookie(new jakarta.servlet.http.Cookie("JSESSIONID", sessionId));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "Welcome " + user.getUsername());
        responseMap.put("role", user.getRole());

        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getUserProfile(HttpServletRequest request) {
        User user = getUserFromSession(request);

        Map<String, Object> profile = new HashMap<>();
        profile.put("email", user.getEmail());
        profile.put("role", user.getRole());

        return ResponseEntity.ok(profile);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            jakarta.servlet.http.Cookie logoutCookie = new jakarta.servlet.http.Cookie("JSESSIONID", null);
            logoutCookie.setMaxAge(0);
            logoutCookie.setPath("/");
            response.addCookie(logoutCookie);

            return ResponseEntity.ok("User logged out successfully");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session to log out");
    }

}


