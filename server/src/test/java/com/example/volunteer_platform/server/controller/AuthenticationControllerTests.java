package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.AuthenticationService;
import com.example.volunteer_platform.shared_dto.UserLoginDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTests {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void testLogin_Success() {
        UserLoginDTO loginRequest = new UserLoginDTO();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("password123");

        User user = mock(User.class);
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        when(authenticationService.authenticate(anyString(), anyString())).thenReturn(user);
        when(userMapper.toUserResponseDTO(user)).thenReturn(userResponseDTO);
        when(request.getSession(true)).thenReturn(session);

        ResponseEntity<UserResponseDTO> responseEntity = authenticationController.login(loginRequest, request, response);

        assertEquals(200, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        verify(authenticationService, times(1)).authenticate(anyString(), anyString());
        verify(request, times(1)).getSession(true);
    }

    @Test
    void testGetUserProfile_Success() {
        User user = mock(User.class);
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(userMapper.toUserResponseDTO(user)).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> responseEntity = authenticationController.getUserProfile(request);

        assertEquals(200, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        verify(request, times(1)).getSession(false);
        verify(session, times(1)).getAttribute("currentUser");
    }


    @Test
    void testLogout_Success() {
        HttpSession session = mock(HttpSession.class);

        when(request.getSession(false)).thenReturn(session);

        ResponseEntity<Boolean> responseEntity = authenticationController.logout(request, response);

        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(Boolean.TRUE, responseEntity.getBody());
        verify(session, times(1)).invalidate();
    }

    @Test
    void testLogout_Failure_NoActiveSession() {
        when(request.getSession(false)).thenReturn(null);

        ResponseEntity<Boolean> responseEntity = authenticationController.logout(request, response);

        assertEquals(401, responseEntity.getStatusCode().value());
        assertEquals(Boolean.FALSE, responseEntity.getBody());
    }
}
