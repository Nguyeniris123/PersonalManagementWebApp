/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.HealthProfile;
import com.nhom20.pojo.UserAccount;
import com.nhom20.services.HealthProfileService;
import com.nhom20.services.UserService;
import java.security.Principal;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("/secure/health-profiles")
    @ResponseBody
    public ResponseEntity<HealthProfile> getMyHealthProfile(Principal principal) {
        String username = principal.getName(); // Lấy username từ token

        if (username == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserAccount user = this.userService.getUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HealthProfile profile = healthProfileService.getHealthProfileByUserId(user.getId());
        if (profile == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(profile, HttpStatus.OK);
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

    @PostMapping("secure/health-profile/add")
    public ResponseEntity<?> addHealthProfile(@RequestBody HealthProfile hp, Principal principal) {
        try {
            String username = principal.getName(); // Lấy username từ người dùng đang đăng nhập
            UserAccount user = this.userService.getUserByUsername(username); // Tìm UserAccount tương ứng

            if (user == null) {
                return new ResponseEntity<>("Không tìm thấy người dùng!", HttpStatus.BAD_REQUEST);
            }

            hp.setUserId(user); // Gán user vào health profile

            // Tính BMI nếu có height & weight
            if (hp.getHeight() > 0 && hp.getWeight() > 0) {
                float heightInMeters = hp.getHeight() / 100f;
                float bmi = hp.getWeight() / (heightInMeters * heightInMeters);
                hp.setBmi(bmi);
            } else {
                hp.setBmi(0); // hoặc null
            }

            hp.setUpdatedAt(new Date()); // Set thời gian cập nhật

            return new ResponseEntity<>(this.healthProfileService.saveHealthProfile(hp), HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace(); // Log lỗi
            return ResponseEntity.badRequest().body("Có lỗi xảy ra khi lưu hồ sơ!");
        }
    }

}
