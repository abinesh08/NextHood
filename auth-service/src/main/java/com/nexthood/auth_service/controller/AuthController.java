package com.nexthood.auth_service.controller;

import com.nexthood.auth_service.dto.AuthRequestDTO;
import com.nexthood.auth_service.dto.AuthResponse;
import com.nexthood.auth_service.dto.SignUpRequestDTO;
import com.nexthood.auth_service.model.User;
import com.nexthood.auth_service.service.UserService;
import com.nexthood.auth_service.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager manager, JwtUtil jwtUtil, UserService userService) {
        this.manager = manager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO requestDTO) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword())
        );

        String token = jwtUtil.generateToken(requestDTO.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDTO dto) {
        return userService.register(dto);
    }
}
