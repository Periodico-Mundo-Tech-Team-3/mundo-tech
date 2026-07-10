package com.mundotech.newspaper.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mundotech.newspaper.dto.response.RoleInfoDto;
import com.mundotech.newspaper.entity.Role;
import com.mundotech.newspaper.mapper.RoleMapper;
import com.mundotech.newspaper.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService, RoleMapper roleMapper){
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @PostMapping
    public ResponseEntity<RoleInfoDto> createRole(@Valid @RequestBody Role role){
        RoleInfoDto response = roleMapper.toRoleInfoDto(roleService.createRole(role));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<RoleInfoDto>> getAllRoles(){
        List<RoleInfoDto> responses = roleService.getAllRoles().stream()
            .map(roleMapper::toRoleInfoDto)
            .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}