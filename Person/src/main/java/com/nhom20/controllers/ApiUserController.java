/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.UserAccount;
import com.nhom20.services.UserService;
import com.nhom20.utils.JwtUtils;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nguyenho
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private UserService userDetailsService;

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") int id) {
        this.userDetailsService.deleteUser(id);
    }

    @PostMapping(path = "/users",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestParam Map<String, String> params, @RequestParam(value = "avatar") MultipartFile avatar) {
        try {
            return new ResponseEntity<>(this.userDetailsService.addUser(params, avatar), HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAccount u) {
        if (this.userDetailsService.authenticate(u.getUsername(), u.getPassword())) {
            try {
                UserAccount user = userDetailsService.getUserByUsername(u.getUsername());
                String token = JwtUtils.generateToken(user.getUsername(), user.getRole());
                return ResponseEntity.ok(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Lỗi khi tạo JWT");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }

    @GetMapping("/secure/profile")
    @ResponseBody
    public ResponseEntity<UserAccount> getProfile(Principal principal) {
        String username = principal.getName();
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserAccount user = this.userDetailsService.getUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("secure/trainers")
    public ResponseEntity<List<UserAccount>> getAllTrainers() {
        List<UserAccount> trainers = userDetailsService.findByRole("ROLE_TRAINER");
        return ResponseEntity.ok(trainers);
    }

    @PutMapping("/secure/user/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") int userId,
            @RequestParam Map<String, String> params,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            Principal principal) {
        try {
            String username = principal.getName();
            UserAccount currentUser = userDetailsService.getUserByUsername(username);

            if (currentUser == null) {
                return new ResponseEntity<>("Không tìm thấy người dùng!", HttpStatus.BAD_REQUEST);
            }

            if (!currentUser.getId().equals(userId) && !currentUser.getRole().equals("ADMIN")) {
                return new ResponseEntity<>("Không có quyền chỉnh sửa thông tin của người khác!", HttpStatus.FORBIDDEN);
            }

            userDetailsService.updateUser(params, avatar, userId);

            return new ResponseEntity<>("Cập nhật thông tin thành công", HttpStatus.OK);

        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
