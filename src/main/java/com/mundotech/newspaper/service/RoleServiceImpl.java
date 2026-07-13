package com.mundotech.newspaper.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mundotech.newspaper.dto.response.RoleInfoDto;
import com.mundotech.newspaper.entity.Role;
import com.mundotech.newspaper.mapper.RoleMapper;
import com.mundotech.newspaper.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl( RoleRepository roleRepository, RoleMapper roleMapper){
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Set<Role> getAllRoles(List<Integer> rolesIds) {
        Set<Role> roles = roleRepository.findAllById(rolesIds).stream().collect(Collectors.toSet());
        return roles;
    }

    @Override
    public List<RoleInfoDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        
        if(roles.isEmpty()){
            throw new RuntimeException("No existen roles");
        }

        return roleMapper.toRoleInfoDtoList(roles);
    }
}