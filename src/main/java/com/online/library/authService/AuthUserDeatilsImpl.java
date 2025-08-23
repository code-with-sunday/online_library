package com.online.library.authService;

import com.online.library.common.Role;
import com.online.library.dto.request.LoginRequest;
import com.online.library.dto.request.UserRequest;
import com.online.library.dto.response.AuthResponse;
import com.online.library.model.User;
import com.online.library.repository.UserRepository;
import com.online.library.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthUserDeatilsImpl implements AuthUserDetails{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsImpl customUserDetails;


    @Override
    public AuthResponse userSignup(UserRequest userSignUpRequest) throws Exception {
        User user = new User();
        user.setEmail(userSignUpRequest.getEmail());

        boolean isEmailExist  = userRepository.existsByEmail(user.getEmail());
        if(isEmailExist){
            user.setEmail(userSignUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
            User savedUser = userRepository.save(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateToken(authentication);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setTitle("Welcome " + user.getEmail());
            authResponse.setMessage("Registration successful");
            authResponse.setRole(savedUser.getRole());
            return authResponse;
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("User Already exist, Please sign in");
        return authResponse;
    }


    @Override
    public AuthResponse signIn(LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? String.valueOf(Role.READER) : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Login success");
        authResponse.setTitle(jwt);
        authResponse.setRole(Role.valueOf(role));

        return authResponse;
    }



    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid username...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password....");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
    }
}
