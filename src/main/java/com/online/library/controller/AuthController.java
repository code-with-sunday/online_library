package com.online.library.controller;

import com.online.library.authService.AuthUserDeatilsImpl;
import com.online.library.dto.request.LoginRequest;
import com.online.library.dto.request.UserRequest;
import com.online.library.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@RestController
public class AuthController {
    private final AuthUserDeatilsImpl authUserDeatils;

    @PostMapping("/sign-up")
    public AuthResponse userSignup(@RequestBody UserRequest userSignUpRequest) throws Exception {
        AuthResponse authResponse = authUserDeatils.userSignup(userSignUpRequest);
        return authResponse;
    }

    @PostMapping("/login")
    public AuthResponse userLogin(@RequestBody LoginRequest loginRequest) throws Exception {
        return authUserDeatils.signIn(loginRequest);
    }
}
