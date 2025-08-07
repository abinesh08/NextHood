package com.nexthood.user_service.mapper;

import com.nexthood.user_service.dto.UserDto;
import com.nexthood.user_service.model.User;

public class UserMapper {
    public static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .location(user.getLocation())
                .phoneNumber(user.getPhoneNumber())
                .build();

    }
    public static User toEntity(UserDto dto){
        return User.builder().
                id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .location(dto.getLocation())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
}
