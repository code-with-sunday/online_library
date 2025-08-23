package com.online.library.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.online.library.common.Role;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String Title;
    private String message;
    private Role role;
}
