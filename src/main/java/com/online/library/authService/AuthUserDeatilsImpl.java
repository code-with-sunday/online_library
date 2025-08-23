package com.online.library.authService;

import com.online.library.common.Role;
import com.online.library.dto.request.LoginRequest;
import com.online.library.dto.request.UserRequest;
import com.online.library.dto.response.AuthResponse;
import com.online.library.model.User;
import com.online.library.repository.UserRepository;
import com.online.library.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@Slf4j
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
        log.info("Making checks if Email exists before creating user : {}", isEmailExist);
        if(!isEmailExist){
            user.setEmail(userSignUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
            user.setFirstName(userSignUpRequest.getFirstName());
            user.setLastName(userSignUpRequest.getLastName());
            user.setEnabled(true);
            user.setRole(userSignUpRequest.getRole());
            User savedUser = userRepository.save(user);
            log.info("user created saved to DB : {}", savedUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Welcome " + user.getFirstName() + " " + user.getLastName());
            authResponse.setStatus(HttpStatus.CREATED.toString());
            authResponse.setRole(savedUser.getRole());
            return authResponse;
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("User Already exist, Please sign in");
        log.info("Exception occurred, user email already exist : {}", userSignUpRequest.getEmail() );
        return authResponse;
    }


    @Override
    public AuthResponse signIn(LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        log.info("Authenticating user... : {}", username);

        Authentication authentication = authenticate(username, password);
        log.info("User Authenticated : {} {}", username, password );
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String authority = authorities.isEmpty()
                ? Role.READER.name()
                : authorities.iterator().next().getAuthority();

        String roleName = authority.replace("ROLE_", "");
        Role role = Role.valueOf(roleName);

        String jwt = jwtProvider.generateToken(authentication);
        log.info("Generating token for authenticated user: {} {}", username, password );
        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(HttpStatus.CREATED.toString());
        authResponse.setMessage(jwt);
        authResponse.setRole(role);
        log.info("User Authenticated with response sent: {}", authResponse );

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
