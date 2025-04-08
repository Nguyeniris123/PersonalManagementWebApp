/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.UserAccount;
import com.nhom20.services.HealthProfileService;
import com.nhom20.services.UserService;
import com.nhom20.services.WorkoutPlanService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
public class indexController {

    @Autowired
    private HealthProfileService healthProfileService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private WorkoutPlanService workoutPlanService;

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("healthProfiles", this.healthProfileService.getHealthProfiles(params));
        return "index";
    }

    @GetMapping("/users")
    public String userList(@RequestParam(name = "username", required = false) String username, Model model) {
        List<UserAccount> users;
        if (username != null && !username.isEmpty()) {
            users = userService.searchUsersByUsername(username);
        } else {
            users = userService.getAllUsers();
        }
        model.addAttribute("users", users);
        return "users";
    }
    
    @GetMapping("/workoutplans")
    public String workoutPlanList(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("workoutPlans", this.workoutPlanService.getWorkOutPlan(params));
        return "workoutplans";
    }
}
