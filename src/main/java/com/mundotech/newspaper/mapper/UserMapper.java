package com.mundotech.newspaper.mapper;

import org.springframework.stereotype.Component;

import com.mundotech.newspaper.dto.request.UserDto;
import com.mundotech.newspaper.entity.User;

@Component
public class UserMapper {
    
    public User toUserEntity(UserDto userDto) {

        if (userDto == null) return null;

        User user = new User();
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
    
        return user;
    }
}
