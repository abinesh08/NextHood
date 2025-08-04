package com.nexthood.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexthood.user_service.config.SecurityConfig;
import com.nexthood.user_service.dto.UserDto;
import com.nexthood.user_service.model.Role;
import com.nexthood.user_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    private UserDto dto;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        dto=UserDto.builder()
                .id(1L)
                .name("John")
                .email("john@Gmail.com")
                .role("RESIDENT")
                .location(("Chennai"))
                .phoneNumber("9999999999")
                .build();
    }
    @Test
    void testCreateUser() throws Exception{
        Mockito.when(userService.createUser(any(UserDto.class))).thenReturn(dto);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));

    }
    @Test
    void testUpdateUser() throws Exception{
        Mockito.when(userService.updateUser(eq(1L), any(UserDto.class))).thenReturn(dto);
        mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@Gmail.com"));
    }
    @Test
    void testGetUserById() throws Exception{
        Mockito.when(userService.getUserById(Long.valueOf(1L))).thenReturn(dto);
        mockMvc.perform(get("/user/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
    @Test
    void testGetUserByName() throws Exception{
        Mockito.when(userService.getUserByName("John")).thenReturn(dto);
        mockMvc.perform(get("/user/name/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }
    @Test
    void testGetUserByRole() throws Exception{
        Mockito.when(userService.getUserByRole(Role.RESIDENT)).thenReturn(List.of(dto));
        mockMvc.perform(get("/user/role/RESIDENT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
    @Test
    void testGetAllUsers() throws Exception {
        Mockito.when(userService.getAllUser()).thenReturn(List.of(dto));

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted Successfully"));
    }
}