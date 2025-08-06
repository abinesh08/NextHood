package com.nexthood.auth_service.config;


import com.nexthood.common_security.JwtFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void publicEndpoint_shouldBeAccessible_withoutAuthentication() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .with(csrf()) // if CSRF is enabled in some other profile
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"password\":\"password\"}"))
                .andExpect(status().isOk()); // this assumes the controller doesn't throw internally
    }


    @Test
    void passwordEncoder_shouldEncodeAndMatch() {
        String rawPassword = "password123";
        String encoded = passwordEncoder.encode(rawPassword);

        assertThat(encoded).isNotEqualTo(rawPassword);
        assertThat(passwordEncoder.matches(rawPassword, encoded)).isTrue();
    }

}