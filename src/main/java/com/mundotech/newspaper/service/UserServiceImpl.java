package com.mundotech.newspaper.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;



import org.springframework.stereotype.Service;

import com.mundotech.newspaper.entity.Role;
import com.mundotech.newspaper.entity.User;
import com.mundotech.newspaper.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService){
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public User createUser(User user, List<Integer> rolesIds) {
        Set<Role> roles = roleService.getAllRoles(rolesIds);
        user.setRoles(roles);
        return userRepository.save(user);
    }
// Este metodo se usará cuando se realice la creación del artículo para ver si existe el usuario
    @Override
    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new RuntimeException("No existe ese usuario");
        }
        return user.get();
      
    }

}
