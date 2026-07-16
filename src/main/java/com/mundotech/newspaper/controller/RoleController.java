package com.mundotech.newspaper.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mundotech.newspaper.dto.response.RoleInfoDto;
import com.mundotech.newspaper.entity.Role;
import com.mundotech.newspaper.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role role){
        Role response = roleService.createRole(role);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<RoleInfoDto>> getAllRoles(){
        List<RoleInfoDto> responses = roleService.getAllRoles();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}