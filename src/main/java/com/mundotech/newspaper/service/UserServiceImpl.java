package com.mundotech.newspaper.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mundotech.newspaper.dto.response.UserInfoDto;
import com.mundotech.newspaper.entity.Role;
import com.mundotech.newspaper.entity.User;
import com.mundotech.newspaper.mapper.UserMapper;
import com.mundotech.newspaper.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, UserMapper userMapper){
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(User user, List<Integer> rolesIds) {
        Set<Role> roles = roleService.getAllRoles(rolesIds);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public UserInfoDto getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new RuntimeException("No existe ese usuario");
        }
        return userMapper.toUserInfoDto(user.get());
    }

    @Override
    public User getUserEntityById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<UserInfoDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new RuntimeException("No existen usuarios");
        }

        return userMapper.toUserInfoDtoList(users);
    }

    @Override
    public void deleteUserById(int id) {
        User user = getUserEntityById(id);
        userRepository.delete(user);
    }    
}