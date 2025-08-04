package com.nexthood.auth_service.dto;

import lombok.Data;

@Data
public class SignUpRequestDTO {
    private String username;
    private String password;
    private String role;
}
