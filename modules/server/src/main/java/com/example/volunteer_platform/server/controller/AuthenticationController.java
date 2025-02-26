package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.AuthenticationService;
import com.example.volunteer_platform.shared_dto.UserLoginDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.volunteer_platform.server.utils.SessionUtils.getUserFromSession;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger serverLogger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody UserLoginDTO loginRequest, HttpServletRequest request, HttpServletResponse response) {
        serverLogger.info("Incoming request to /auth/login with payload: {}", loginRequest);

        User user = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        serverLogger.info(user.toString());

        HttpSession session = request.getSession(true);
        session.setAttribute("currentUser", user);

        String sessionId = session.getId();
        response.addCookie(new jakarta.servlet.http.Cookie("JSESSIONID", sessionId));

        UserResponseDTO userResponse = userMapper.toUserResponseDTO(user);

        serverLogger.info("Responding with status {} and payload: {}", HttpStatus.OK, userResponse);

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getUserProfile(HttpServletRequest request) {
        serverLogger.info("Incoming request to /auth/profile");

        User user = getUserFromSession(request);

        UserResponseDTO userResponse = userMapper.toUserResponseDTO(user);

        serverLogger.info("Responding with status {} and payload: {}", HttpStatus.OK, userResponse);

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(HttpServletRequest request, HttpServletResponse response) {
        serverLogger.info("Incoming request to /auth/logout");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            jakarta.servlet.http.Cookie logoutCookie = new jakarta.servlet.http.Cookie("JSESSIONID", null);
            logoutCookie.setMaxAge(0);
            logoutCookie.setPath("/");
            response.addCookie(logoutCookie);

            serverLogger.info("User successfully logged out");

            return ResponseEntity.ok(true);
        }

        serverLogger.warn("Logout failed: no active session found");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
}
