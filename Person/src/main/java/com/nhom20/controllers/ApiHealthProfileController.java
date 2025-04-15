/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.HealthProfile;
import com.nhom20.services.HealthProfileService;
import com.nhom20.services.UserService;
import java.util.Date;
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
public class ApiHealthProfileController {

    @Autowired
    private HealthProfileService healthProfileService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/health-profiles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") int id) {
        this.healthProfileService.deleteHealthProfile(id);
    }

    @PostMapping("/health-profile/add")
    public ResponseEntity<?> addHealthProfile(@RequestBody HealthProfile hp) {
        try {
            if (hp.getHeight() > 0 && hp.getWeight() > 0) {
                hp.setHeight(hp.getHeight() / 100);
                float bmi = hp.getWeight() / (hp.getHeight() * hp.getHeight());
                hp.setBmi(bmi);
            } else {
                hp.setBmi(0); // hoặc để null
            }
            hp.setUpdatedAt(new Date()); // Đặt thời gian hiện tại
            return new ResponseEntity<>(this.healthProfileService.saveHealthProfile(hp), HttpStatus.CREATED); // Trả về trạng thái CREATED (201)
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);  // Trả về lỗi nếu có vấn đề
        }
    }

    @GetMapping("secure/health-profiles/user/{userId}")
    public ResponseEntity<HealthProfile> getHealthProfileByUserId(@PathVariable(value = "userId") int userId) {
        // Gọi service để lấy hồ sơ sức khỏe của người dùng theo userId
        HealthProfile healthProfile = healthProfileService.getHealthProfileByUserId(userId);

        // Kiểm tra nếu không tìm thấy hồ sơ sức khỏe
        if (healthProfile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(healthProfile);
    }

    // API lấy hồ sơ sức khỏe theo id
    @GetMapping("secure/health-profiles/{id}")
    public ResponseEntity<HealthProfile> getHealthProfileById(@PathVariable(value = "id") int id) {
        HealthProfile healthProfile = healthProfileService.getHealthProfileById(id);

        if (healthProfile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(healthProfile);
    }

}
