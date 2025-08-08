package com.nexthood.user_service.service;

import com.nexthood.user_service.dto.UserDto;
import com.nexthood.user_service.exception.ResourceNotFoundException;
import com.nexthood.user_service.model.User;
import com.nexthood.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto dto;

    @BeforeEach
    void setup(){
        user= new User(1L, "Abinesh","abinesh@gmail.com","Chennai","9999999999");
        dto = new UserDto(1L, "Abinesh", "abinesh@gmail.com", "Chennai", "9999999999");
    }
    @Test
    void testCreateUser(){
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto result= userService.createUser(dto);
        assertThat(result.getName()).isEqualTo("Abinesh");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUserSuccess(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        dto.setName("Updated");
        UserDto updated= userService.updateUser(1L,dto);
        assertThat(updated.getName()).isEqualTo("Updated");
    }
    @Test
    void testUpdateUser_NotFound(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, ()-> userService.updateUser(1L, dto));
    }
    @Test
    void testGetUserByNameSuccess(){
        when(userRepository.findByName("Abinesh")).thenReturn(Optional.of(user));
        UserDto result= userService.getUserByName("Abinesh");
        assertThat(result.getEmail()).isEqualTo("abinesh@gmail.com");
    }
    @Test
    void testGetUserByName_NotFound(){
        when(userRepository.findByName("Unknown")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->userService.getUserByName("Unknown"));
    }
    @Test
    void testGetUserByIdSuccess(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDto result= userService.getUserById(1L);
        assertThat(result.getId()).isEqualTo(1L);
    }
    @Test
    void testGetUserById_NotFound(){
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->userService.getUserById(2L));
    }
    @Test
    void testGetAllUser(){
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDto> result= userService.getAllUser();
        assertThat(result).hasSize(1);
    }
    @Test
    void testDeleteUserSuccess(){
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }
    @Test
    void testDeleteUser_NotFound(){
        when(userRepository.existsById(2L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,()-> userService.deleteUser(2L));
    }

}