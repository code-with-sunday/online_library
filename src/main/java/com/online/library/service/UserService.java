package com.online.library.service;

import com.online.library.dto.request.UserRequest;
import com.online.library.dto.response.UserResponse;
import com.online.library.exception.UserAlreadyExistException;
import com.online.library.model.User;
import com.online.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserResponse saveOrUpdate(UserRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserAlreadyExistException("User with email: {} already exists" + request.getEmail()));

        mapToUser(request, user);

        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setRole(user.getRole());
        response.setProfileImageUrl(user.getProfileImageUrl());
        response.setEnabled(user.isEnabled());
        response.setCreatedDate(user.getCreateDate());
        response.setUpdatedDate(user.getUpdateDate());
        return response;
    }

    private void mapToUser(UserRequest request, User user) {
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());
        user.setProfileImageUrl(request.getProfileImageUrl());

        if (request.getEnabled() != null) {
            user.setEnabled(request.getEnabled());
        }
    }
}
