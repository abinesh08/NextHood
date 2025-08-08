package com.nexthood.common_security;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTests {

    @Autowired
    private JwtUtil jwtUtil;

    private final Role defaultRole = Role.RESIDENT;
    @Test
    void testGenerateToken_shouldReturnValidToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username,defaultRole);

        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
    }

    @Test
    public void testExtractUsername_shouldMatchOriginal() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username,defaultRole);
        String extracted = jwtUtil.extractUsername(token);

        assertEquals(username, extracted);
    }

    @Test
    public void testValidateToken_shouldReturnTrueForValid() {
        String username = "validuser";
        String token = jwtUtil.generateToken(username, defaultRole);

        assertTrue(jwtUtil.validateToken(token, username));
    }

    @Test
    public void testValidateToken_shouldReturnFalseForInvalidUsername() {
        String token = jwtUtil.generateToken("user1",defaultRole);

        assertFalse(jwtUtil.validateToken(token, "user2"));
    }

}

