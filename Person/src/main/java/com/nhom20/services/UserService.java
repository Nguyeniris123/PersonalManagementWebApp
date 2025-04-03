/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.services;

import com.nhom20.pojo.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author nguyenho
 */

public interface UserService extends UserDetailsService {
    UserAccount getUserById(int id);
    UserAccount getUserByUsername(String username);
    UserAccount createUser(UserAccount user);
    UserAccount updateUser(UserAccount user);
    boolean deleteUser(int id);
}