package com.nexthood.auth_service.dto;

import com.nexthood.common_security.Role;
import lombok.Data;

@Data
public class AuthRequestDTO {
    private String username;
    private String password;
    private Role role;
}
