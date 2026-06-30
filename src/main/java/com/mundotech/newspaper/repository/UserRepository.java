package com.mundotech.newspaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundotech.newspaper.entity.User;


public interface UserRepository extends JpaRepository<User,Integer> {

    

}
