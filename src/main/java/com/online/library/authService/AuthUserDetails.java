package com.online.library.authService;

import com.online.library.dto.request.LoginRequest;
import com.online.library.dto.request.UserRequest;
import com.online.library.dto.response.AuthResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthUserDetails {

    AuthResponse signIn(@RequestBody LoginRequest loginRequest);
    AuthResponse userSignup(@RequestBody UserRequest userSignUpRequest) throws Exception;
}

