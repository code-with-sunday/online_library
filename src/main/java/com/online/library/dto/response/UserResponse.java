package com.online.library.dto.response;


import com.online.library.common.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private String profileImageUrl;
    private boolean enabled;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}