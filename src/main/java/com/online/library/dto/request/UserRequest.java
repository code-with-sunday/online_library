package com.online.library.dto.request;

import com.online.library.common.Role;
import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    private String profileImageUrl;
    private Boolean enabled;
}
