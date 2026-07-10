package com.mundotech.newspaper.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mundotech.newspaper.dto.request.UserDto;
import com.mundotech.newspaper.dto.response.UserInfoDto;
//import com.mundotech.newspaper.dto.response.UserResponse;
import com.mundotech.newspaper.entity.User;
import com.mundotech.newspaper.mapper.UserMapper;
import com.mundotech.newspaper.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper){
            this.userService = userService;
            this.userMapper = userMapper; 
    }

    // @PostMapping
    // public ResponseEntity<UserInfoDto> createUser(@Valid @RequestBody UserDto userDto, @RequestParam List<Integer> rolesIds){
    //     User user = userMapper.toUserEntity(userDto);
    //     UserInfoDto response = userMapper.toUserInfoDto(userService.createUser(user, rolesIds));
    //     return new ResponseEntity<>(response, HttpStatus.CREATED); 
    // }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, @RequestParam List<Integer> rolesIds){

        return new ResponseEntity<>(userService.createUser(user, rolesIds), HttpStatus.CREATED); 
    }

    @GetMapping
    public ResponseEntity<List<UserInfoDto>> getAllUsers(){
        List<UserInfoDto> responses = userService.getAllUsers().stream()
            .map(userMapper::toUserInfoDto)
            .toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}