package com.nexthood.auth_service.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class JwtUtilTests {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void testGenerateAndExtractUsername() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testValidateToken_ValidToken() {
        String username = "validUser";
        String token = jwtUtil.generateToken(username);

        assertTrue(jwtUtil.validateToken(token, username));
    }

    @Test
    void testValidateToken_InvalidUsername() {
        String username = "userA";
        String token = jwtUtil.generateToken(username);

        assertFalse(jwtUtil.validateToken(token, "userB"));
    }

    @Test
    void testIsTokenExpired_False() {
        String username = "notExpired";
        String token = jwtUtil.generateToken(username);

        assertFalse(tokenIsExpired(token));
    }

    private boolean tokenIsExpired(String token) {
        return !jwtUtil.validateToken(token, jwtUtil.extractUsername(token));
    }
}
