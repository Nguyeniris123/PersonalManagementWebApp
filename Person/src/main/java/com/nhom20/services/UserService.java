/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.services;

import com.nhom20.pojo.UserAccount;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nguyenho
 */

public interface UserService extends UserDetailsService {
    UserAccount getUserById(int id);
    UserAccount getUserByUsername(String username);
    List<UserAccount> getAllUsers();
    List<UserAccount> searchUsersByUsername(String username);
    UserAccount addUser(Map<String, String> params, MultipartFile avatar);
    UserAccount updateUser(UserAccount user);
    boolean deleteUser(int id);
}