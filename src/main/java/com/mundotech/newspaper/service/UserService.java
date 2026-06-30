package com.mundotech.newspaper.service;

import java.util.List;

import com.mundotech.newspaper.entity.User;

public interface UserService {

    public User createUser(User user, List<Integer> rolesIds);

    public User getUserById(int id);
    

}
