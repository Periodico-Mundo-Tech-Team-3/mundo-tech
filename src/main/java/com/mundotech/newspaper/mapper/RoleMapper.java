package com.mundotech.newspaper.mapper;

import java.util.Set;

import org.springframework.stereotype.Component;

//import com.mundotech.newspaper.dto.request.RoleDto;
import com.mundotech.newspaper.dto.response.RoleInfoDto;
import com.mundotech.newspaper.entity.Role;

import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public RoleInfoDto toRoleInfoDto(Role role){
        if (role == null) return null;

        return new RoleInfoDto(
            role.getId(),
            role.getName()
        );
    }

    public Set<RoleInfoDto> toRoleInfoDtoSet(Set<Role> roles){
        return roles.stream()
            .map(this::toRoleInfoDto)
            .collect(Collectors.toSet());
    }
}
