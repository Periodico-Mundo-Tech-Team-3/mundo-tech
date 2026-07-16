package com.mundotech.newspaper.mapper;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.mundotech.newspaper.dto.request.UserDto;
import com.mundotech.newspaper.dto.response.RoleInfoDto;
import com.mundotech.newspaper.dto.response.UserInfoDto;
import com.mundotech.newspaper.entity.User;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    
    public User toUserEntity(UserDto userDto) {

        if (userDto == null) return null;

        User user = new User();
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
    
        return user;
    }

    public UserInfoDto toUserInfoDto(User user){
        if (user == null) return null;

        Set<RoleInfoDto> roleResponses = user.getRoles().stream()
            .map(role -> new RoleInfoDto(role.getId(), role.getName()))
            .collect(Collectors.toSet());

        return new UserInfoDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            roleResponses
        );
    }

    public List<UserInfoDto> toUserInfoDtoList(List<User> users) {
        return users.stream()
            .map(this::toUserInfoDto)
            .collect(Collectors.toList());
    }
}
