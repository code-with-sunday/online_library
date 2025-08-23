package com.online.library.controller;

import com.online.library.authService.AuthUserDeatilsImpl;
import com.online.library.common.Role;
import com.online.library.dto.request.LoginRequest;
import com.online.library.dto.request.UserRequest;
import com.online.library.dto.response.AuthResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthUserDeatilsImpl authUserDeatils;

    @InjectMocks
    private AuthController authController;

    private UserRequest testUserRequest;
    private LoginRequest testLoginRequest;
    private AuthResponse expectedAuthResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUserRequest = new UserRequest();
        testUserRequest.setEmail("sundaypetertest@gmail.com");
        testUserRequest.setPassword("password123");
        testUserRequest.setFirstName("sunday");
        testUserRequest.setLastName("peter");
        testUserRequest.setRole(Role.READER);
        testUserRequest.setEnabled(true);
        testUserRequest.setProfileImageUrl("http://example.com/profile.jpg");

        expectedAuthResponse = new AuthResponse();
        expectedAuthResponse.setStatus("200");
        expectedAuthResponse.setMessage("success");
    }

    @Test
    void testUserSignup() throws Exception {

        when(authUserDeatils.userSignup(testUserRequest)).thenReturn(expectedAuthResponse);

        AuthResponse actualResponse = authController.userSignup(testUserRequest);

        assertNotNull(actualResponse);
        assertEquals(expectedAuthResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedAuthResponse.getMessage(), actualResponse.getMessage());

        verify(authUserDeatils, times(1)).userSignup(testUserRequest);
    }

    @Test
    void testUserLogin() throws Exception {
        when(authUserDeatils.signIn(any())).thenReturn(expectedAuthResponse);

        AuthResponse authResponse = authController.userLogin(testLoginRequest);

        assertNotNull(authResponse);
        assertEquals(expectedAuthResponse.getStatus(), authResponse.getStatus());
        assertEquals(expectedAuthResponse.getMessage(), authResponse.getMessage());
    }
}

