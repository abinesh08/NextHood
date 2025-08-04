package com.nexthood.user_service.repository;

import com.nexthood.user_service.model.Role;
import com.nexthood.user_service.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Test findByName() returns correct user")
    void testFindByName(){
        User user= User.builder()
                .name("Arun")
                .email("arun@gmail.com")
                .location("Coimbatore")
                .phoneNumber("7777777777")
                .role(Role.RESIDENT)
                .build();

        userRepository.save(user);
        Optional<User> result= userRepository.findByName("Arun");
        assertTrue(result.isPresent());
        assertEquals("Arun", result.get().getName());
        assertEquals(Role.RESIDENT, result.get().getRole());
    }

    @Test
    @DisplayName("Test findByRole() returns correct user")
    void testFindByRole(){

    }
}