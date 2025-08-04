package com.nexthood.auth_service.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String username;
    private String password;
}
