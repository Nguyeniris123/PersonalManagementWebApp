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
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
        String username = params.get("username");
        String email = params.get("email");

        if (userRepository.getUserByUsername(username) != null) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        }

        if (userRepository.getUserByEmail(email) != null) {
            throw new RuntimeException("Email đã tồn tại");
        }

        UserAccount u = new UserAccount();
        u.setUsername(username);
        u.setFullName(params.get("full_name"));
        u.setEmail(email);
        u.setPhone(params.get("phone"));
        u.setRole(params.get("role"));
        u.setGender(params.get("gender"));
        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        u.setCreatedAt(new Date());

        try {
            String dob = params.get("date_of_birth");
            if (dob != null && !dob.isEmpty()) {
                LocalDate date = LocalDate.parse(dob);
                u.setDateOfBirth(java.sql.Date.valueOf(date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.userRepository.addUser(u);
    }

    @Override
    public UserAccount updateUser(Map<String, String> params, MultipartFile avatar, int userId) {
        UserAccount u = userRepository.getUserById(userId);
        if (u == null) {
            throw new IllegalArgumentException("Người dùng không tồn tại");
        }

        String newUsername = params.get("username");
        String newEmail = params.get("email");

        UserAccount existingUser = userRepository.getUserByUsername(newUsername);
        if (existingUser != null && existingUser.getId() != userId) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        }

        existingUser = userRepository.getUserByEmail(newEmail);
        if (existingUser != null && existingUser.getId() != userId) {
            throw new RuntimeException("Email đã tồn tại");
        }

        u.setUsername(newUsername);
        u.setFullName(params.get("full_name"));
        u.setEmail(newEmail);
        u.setPhone(params.get("phone"));
        u.setRole(params.get("role"));
        u.setGender(params.get("gender"));

        String password = params.get("password");
        if (password != null && !password.isEmpty()) {
            u.setPassword(passwordEncoder.encode(password));
        }

        try {
            String dob = params.get("date_of_birth");
            if (dob != null && !dob.isEmpty()) {
                LocalDate date = LocalDate.parse(dob);
                u.setDateOfBirth(java.sql.Date.valueOf(date));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Định dạng ngày sinh không hợp lệ");
        }

        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                throw new RuntimeException("Lỗi khi tải ảnh đại diện");
            }
        }

        return userRepository.updateUser(u);
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

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepository.authenticate(username, password);
    }

    @Override
    public List<UserAccount> findByRole(String role) {
        return userRepository.findByRole(role);
    }
}
