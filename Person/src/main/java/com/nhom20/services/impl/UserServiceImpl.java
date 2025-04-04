/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.services.impl;

import com.nhom20.pojo.UserAccount;
import com.nhom20.repositories.UserRepository;
import com.nhom20.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nguyenho
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserAccount getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public UserAccount getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public List<UserAccount> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public UserAccount addUser(UserAccount user) {
        return userRepository.addUser(user);
    }

    @Override
    public UserAccount updateUser(UserAccount user) {
        return userRepository.updateUser(user);
    }

    @Override
    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }
}
