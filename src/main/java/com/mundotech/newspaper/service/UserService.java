package com.mundotech.newspaper.service;

import java.util.List;

import com.mundotech.newspaper.dto.response.UserInfoDto;
import com.mundotech.newspaper.entity.User;

public interface UserService {
    public User createUser(User user, List<Integer> rolesIds);

    public UserInfoDto getUserById(int id);

    public User getUserEntityById(int id);
    
    public List<UserInfoDto> getAllUsers();

    public void deleteUserById(int id);
}