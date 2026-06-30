package com.mundotech.newspaper.service;

import java.util.List;
import java.util.Set;

import com.mundotech.newspaper.entity.Role;

public interface RoleService {
    
    public Role createRole(Role role);

    public Set<Role> getAllRoles(List<Integer> rolesIds);

}
