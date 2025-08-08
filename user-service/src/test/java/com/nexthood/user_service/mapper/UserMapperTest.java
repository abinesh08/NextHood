package com.nexthood.user_service.mapper;

import com.nexthood.user_service.dto.UserDto;
import com.nexthood.user_service.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void testToDto(){
        User user= User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .location("Chennai")
                .phoneNumber("999999999")
                .build();

        UserDto dto= UserMapper.toDto(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getLocation(), dto.getLocation());

        assertEquals(user.getPhoneNumber(), dto.getPhoneNumber());
    }
    @Test
    void testToEntity(){
        UserDto dto= UserDto.builder()
                .id(1L)
                .name("Jane")
                .email("jane@gmail.com")
                .location("Mumbai")
                .phoneNumber("9999999999")
                .build();

        User user= UserMapper.toEntity(dto);
        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getLocation(), user.getLocation());

        assertEquals(dto.getPhoneNumber(), user.getPhoneNumber());
    }

}