/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.UserAccount;
import com.nhom20.pojo.WorkoutPlan;
import com.nhom20.services.UserService;
import com.nhom20.services.WorkoutPlanService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nguyenho
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiWorkoutPlanController {

    @Autowired
    private WorkoutPlanService workoutPlanService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/workout-plans/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") int id) {
        this.workoutPlanService.deleteWorkOutPlan(id);
    }

    @PostMapping("/secure/workout-plan/add")
    public ResponseEntity<?> createWorkoutPlan(@RequestBody WorkoutPlan wp, Principal principal) {
        try {
            String username = principal.getName(); 
            UserAccount user = this.userService.getUserByUsername(username);

            if (user == null) {
                return new ResponseEntity<>("Không tìm thấy người dùng!", HttpStatus.BAD_REQUEST);
            }

            if (wp.getEndDate().before(wp.getStartDate())) {
                return ResponseEntity.badRequest().body("Ngày kết thúc không được nhỏ hơn ngày bắt đầu!");
            }

            wp.setUserId(user); 
            wp.setCreatedAt(new Date());

            return new ResponseEntity<>(this.workoutPlanService.addOrUpdateWorkOutPlan(wp), HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Có lỗi xảy ra khi thêm kế hoạch!");
        }
    }

    @GetMapping("/secure/workout-plans")
    public ResponseEntity<?> getMyWorkoutPlans(@RequestParam Map<String, String> params, Principal principal) {
        try {
            String username = principal.getName();
            UserAccount user = userService.getUserByUsername(username);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng không tồn tại");
            }

            List<WorkoutPlan> workoutPlans = workoutPlanService.getWorkoutPlanByUserId(user.getId(), params);

            return ResponseEntity.ok(workoutPlans);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi lấy kế hoạch tập luyện");
        }
    }
}
