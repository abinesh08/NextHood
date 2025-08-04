package com.nexthood.auth_service.service;

import com.nexthood.auth_service.dto.SignUpRequestDTO;
import com.nexthood.auth_service.model.User;
import com.nexthood.auth_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTests {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void register_shouldSaveUser_whenUsernameIsUnique() {
        SignUpRequestDTO dto = new SignUpRequestDTO();
        dto.setUsername("newUser");
        dto.setPassword("password123");
        dto.setRole("ROLE_USER");

        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        User savedUser = User.builder()
                .username("newUser")
                .password("encodedPassword")
                .role("ROLE_USER")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.register(dto);

        assertNotNull(result);
        assertEquals("newUser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals("ROLE_USER", result.getRole());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals("newUser", userCaptor.getValue().getUsername());
    }

    @Test
    void register_shouldThrowException_whenUsernameExists() {
        SignUpRequestDTO dto = new SignUpRequestDTO();
        dto.setUsername("existingUser");
        dto.setPassword("password");
        dto.setRole("ROLE_USER");

        when(userRepository.findByUsername("existingUser"))
                .thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.register(dto));
        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}
