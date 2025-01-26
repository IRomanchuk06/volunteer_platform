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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.volunteer_platform.server.utils.SessionUtils.getUserFromSession;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody UserLoginDTO loginRequest, HttpServletRequest request, HttpServletResponse response) {
        User user = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        HttpSession session = request.getSession(true);
        session.setAttribute("currentUser", user);

        String sessionId = session.getId();
        response.addCookie(new jakarta.servlet.http.Cookie("JSESSIONID", sessionId));

        UserResponseDTO userResponse = userMapper.toUserResponseDTO(user);

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getUserProfile(HttpServletRequest request) {
        User user = getUserFromSession(request);

        UserResponseDTO userResponse = userMapper.toUserResponseDTO(user);

        return ResponseEntity.ok(userResponse);
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


