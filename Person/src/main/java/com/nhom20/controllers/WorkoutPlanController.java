/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.UserAccount;
import com.nhom20.pojo.WorkoutPlan;
import com.nhom20.services.UserService;
import com.nhom20.services.WorkoutPlanService;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author nguyenho
 */

@Controller
public class WorkoutPlanController {
    @Autowired
    private WorkoutPlanService workoutPlanService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/workout-plans-view")
    public String addView(Model model) {
        model.addAttribute("workoutPlan", new WorkoutPlan());
        model.addAttribute("users", userService.getAllUsers());
        return "addupdateworkoutplans";
    }
    
    // Sử dụng InitBinder để chuyển đổi String thành UserAccount
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(UserAccount.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                UserAccount user = userService.getUserById(Integer.parseInt(text));
                setValue(user);
            }
        });
    }
    
    @PostMapping("/workout-plan/add")
    public String addWorkoutPlan(@ModelAttribute("workoutPlan") WorkoutPlan wp, Model model) {
        try {
            wp.setCreatedAt(new Date()); // Đặt thời gian hiện tại
            workoutPlanService.addOrUpdateWorkOutPlan(wp);
            return "redirect:/";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage()); // gắn thông báo lỗi
            model.addAttribute("users", userService.getAllUsers());
            return "healthprofiles"; // giữ lại form
        }
    }
//
//    
//    @GetMapping("/health-profiles/{id}")
//    public String updateView(Model model, @PathVariable(value = "id") int id) {
//        model.addAttribute("healthProfile", this.healthProfileService.getHealthProfileById(id));
//        model.addAttribute("users", userService.getAllUsers());
//        return "healthprofiles";
//    }
}
