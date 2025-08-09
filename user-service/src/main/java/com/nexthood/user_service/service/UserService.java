package com.nexthood.user_service.service;

import com.nexthood.user_service.dto.UserDto;
import com.nexthood.user_service.exception.ResourceNotFoundException;
import com.nexthood.user_service.mapper.UserMapper;
import com.nexthood.user_service.model.User;
import com.nexthood.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @PreAuthorize("hasAnyRole('RESIDENT', 'VOLUNTEER', 'AUTHORITY')")
    public UserDto createUser(UserDto dto){
        User saved= userRepository.save(UserMapper.toEntity(dto));
        return UserMapper.toDto(saved);
    }

    @PreAuthorize("hasAnyRole('RESIDENT', 'VOLUNTEER', 'AUTHORITY')")
    public UserDto updateUser(Long id, UserDto dto){
        User user=userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found: " + id));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setLocation(dto.getLocation());
        user.setPhoneNumber(dto.getPhoneNumber());
        return UserMapper.toDto(userRepository.save(user));
    }
    @PreAuthorize("hasRole('AUTHORITY')")
    public UserDto getUserByName(String name){
        return userRepository.findByName(name)
                .map(UserMapper::toDto)
                .orElseThrow(()->new ResourceNotFoundException("User not found: " + name));
    }
    @PreAuthorize("hasRole('AUTHORITY')")
    public UserDto getUserById(Long id){
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(()->new ResourceNotFoundException("User not found : " + id));
    }
    @PreAuthorize("hasRole('AUTHORITY')")
    public List<UserDto> getAllUser(){
        return userRepository.findAll()
                .stream().map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('AUTHORITY')")
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

}
