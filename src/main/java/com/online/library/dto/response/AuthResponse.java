package com.online.library.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.online.library.common.Role;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String message;
    private String status;
    private Role role;
}
