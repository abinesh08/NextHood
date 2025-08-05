package com.nexthood.user_service.controller;

import com.nexthood.user_service.dto.UserDto;
import com.nexthood.user_service.model.Role;
import com.nexthood.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto create(@RequestBody UserDto dto){
        return userService.createUser(dto);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable("id") Long id, @RequestBody UserDto dto){
        return userService.updateUser(id, dto);
    }

    @GetMapping("/name/{name}")
    public UserDto getByName(@PathVariable("name") String name){
        return userService.getUserByName(name);
    }

    @GetMapping("/id/{id}")
    public UserDto getById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/role/{role}")
    public List<UserDto> getUserByRole(@PathVariable("role") Role role){
        return userService.getUserByRole(role);
    }

    @GetMapping
    public List<UserDto> getAll(){
        return userService.getAllUser();
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "User deleted Successfully";
    }
}
