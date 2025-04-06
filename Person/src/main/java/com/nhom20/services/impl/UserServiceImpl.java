/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nhom20.pojo.UserAccount;
import com.nhom20.repositories.UserRepository;
import com.nhom20.services.UserService;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nguyenho
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private Cloudinary cloudinary;
    
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
    public List<UserAccount> searchUsersByUsername(String username) {
        return userRepository.searchUsersByUsername(username);
    }

    @Override
    public UserAccount addUser(Map<String, String> params, MultipartFile avatar) {
        UserAccount u = new UserAccount();
        u.setUsername(params.get("username"));
        u.setFullName(params.get("full_name"));
        u.setEmail(params.get("email"));
        u.setPhone(params.get("phone"));
        u.setRole("role");
        u.setGender("gender");
        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        
        
        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return this.userRepository.addUser(u);
    }

    @Override
    public UserAccount updateUser(UserAccount user) {
        return userRepository.updateUser(user);
    }

    @Override
    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole()));
        
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    
}
