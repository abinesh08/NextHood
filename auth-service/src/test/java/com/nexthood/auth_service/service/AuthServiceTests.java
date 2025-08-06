package com.nexthood.auth_service.service;

import com.nexthood.auth_service.model.User;
import com.nexthood.auth_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTests {

    private UserRepository userRepository;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        authService = new AuthService(userRepository);
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        String username = "testUser";
        String password = "testPass";
        String role = "ROLE_USER";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = authService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserNotFound() {
        String username = "nonexistent";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.loadUserByUsername(username));
    }

}
