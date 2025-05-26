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
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/secure/request-user-trainers")
    public ResponseEntity<List<UserTrainer>> getUserTrainerbyTrainer(Principal principal) {
        String username = principal.getName();
        UserAccount user = userService.getUserByUsername(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<UserTrainer> connections = userTrainerService.getUserTrainerByTrainerId(user.getId());

        return new ResponseEntity<>(connections, HttpStatus.OK);
    }

    @PostMapping("/secure/user-trainer/add")
    public ResponseEntity<?> requestTrainer(@RequestBody UserTrainer userTrainer, Principal principal) {
        try {
            String username = principal.getName();

            UserAccount user = userService.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không tìm thấy người dùng!");
            }

            userTrainer.setUserId(user);
            userTrainer.setStatus("PENDING");
            userTrainer.setCreatedAt(new Date());

            UserTrainer saved = userTrainerService.addOrUpdateUserTrainer(userTrainer);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/secure/user-trainer/accept/{id}")
    public ResponseEntity<String> updateStatusToAccepted(@PathVariable("id") int id, Principal principal) {
        try {
            UserTrainer userTrainer = userTrainerService.getUserTrainerById(id);
            if (userTrainer == null) {
                return new ResponseEntity<>("Yêu cầu không tồn tại.", HttpStatus.NOT_FOUND);
            }

            String currentUsername = principal.getName();

            if (!currentUsername.equals(userTrainer.getTrainerId().getUsername())) {
                return new ResponseEntity<>("Bạn không có quyền cập nhật yêu cầu này.", HttpStatus.FORBIDDEN);
            }

            userTrainer.setStatus("ACCEPTED");
            userTrainerService.addOrUpdateUserTrainer(userTrainer);  // Dùng service để lưu cập nhật

            return new ResponseEntity<>("Trạng thái đã được cập nhật thành ACCEPTED.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi cập nhật trạng thái.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/secure/user-trainer/reject/{id}")
    public ResponseEntity<String> updateStatusToRejected(@PathVariable("id") int id, Principal principal) {
        try {
            UserTrainer userTrainer = userTrainerService.getUserTrainerById(id);
            if (userTrainer == null) {
                return new ResponseEntity<>("Yêu cầu không tồn tại.", HttpStatus.NOT_FOUND);
            }

            String currentUsername = principal.getName();

            if (!currentUsername.equals(userTrainer.getTrainerId().getUsername())) {
                return new ResponseEntity<>("Bạn không có quyền cập nhật yêu cầu này.", HttpStatus.FORBIDDEN);
            }

            userTrainer.setStatus("REJECTED");
            userTrainerService.addOrUpdateUserTrainer(userTrainer);

            return new ResponseEntity<>("Trạng thái đã được cập nhật thành REJECTED.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi cập nhật trạng thái.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
