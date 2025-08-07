package com.nexthood.auth_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexthood.auth_service.dto.AuthRequestDTO;
import com.nexthood.auth_service.dto.SignUpRequestDTO;
import com.nexthood.auth_service.model.User;
import com.nexthood.auth_service.repository.UserRepository;
import com.nexthood.auth_service.service.UserService;
import com.nexthood.common_security.JwtFilter;
import com.nexthood.common_security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() throws Exception {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("testUser");
        request.setPassword("password");
        String token = "mocked-jwt-token";

        Authentication mockAuth = Mockito.mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);

        when(jwtUtil.generateToken("testUser")).thenReturn(token);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void signUp_ShouldReturnSavedUser() throws Exception {
        SignUpRequestDTO dto = new SignUpRequestDTO();
        dto.setUsername("newUser");
        dto.setPassword("pass123");
        dto.setRole("ROLE_USER");

        User savedUser = User.builder()
                .id(1L)
                .username("newUser")
                .password("encodedpass")
                .role("ROLE_USER")
                .build();

        when(userService.register(any(SignUpRequestDTO.class))).thenReturn(savedUser);

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newUser"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }
}
