/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.UserAccount;
import com.nhom20.pojo.UserTrainer;
import com.nhom20.services.UserService;
import com.nhom20.services.UserTrainerService;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nguyenho
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserTrainerController {

    @Autowired
    private UserTrainerService userTrainerService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/user-trainer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") int id) {
        this.userTrainerService.deleteUserTrainer(id);
    }

    @GetMapping("/secure/user-trainers")
    public ResponseEntity<List<UserTrainer>> getUserTrainerConnections(Principal principal) {
        String username = principal.getName();
        UserAccount user = userService.getUserByUsername(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<UserTrainer> connections = userTrainerService.getUserTrainerByUserId(user.getId());

        return new ResponseEntity<>(connections, HttpStatus.OK);
    }

    @PostMapping("/secure/user-trainer/add")
    public ResponseEntity<?> requestTrainer(@RequestBody UserTrainer userTrainer, Principal principal) {
        try {
            // Lấy username của user đang đăng nhập
            String username = principal.getName();

            // Tìm user theo username
            UserAccount user = userService.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không tìm thấy người dùng!");
            }

            // Gán userId và các thông tin mặc định
            userTrainer.setUserId(user);
            userTrainer.setStatus("PENDING");
            userTrainer.setCreatedAt(new Date());

            UserTrainer saved = userTrainerService.addOrUpdateUserTrainer(userTrainer);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

}
