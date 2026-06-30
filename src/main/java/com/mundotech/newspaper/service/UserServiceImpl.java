package com.mundotech.newspaper.service;

import org.springframework.stereotype.Service;

import com.mundotech.newspaper.entity.User;
import com.mundotech.newspaper.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }


}
