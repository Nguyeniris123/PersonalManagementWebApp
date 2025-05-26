/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.WorkoutPlan;
import com.nhom20.services.UserService;
import com.nhom20.services.WorkoutPlanService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @PostMapping("/workout-plan/add")
    public String addWorkoutPlan(@ModelAttribute("workoutPlan") WorkoutPlan wp, Model model) {
        try {
        
            wp.setCreatedAt(new Date());
            workoutPlanService.addOrUpdateWorkOutPlan(wp);
            return "redirect:/workout-plans";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("users", userService.getAllUsers());
            return "addupdateworkoutplans";
        }
    }

    
    @GetMapping("/workout-plans/{id}")
    public String updateView(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("workoutPlan", this.workoutPlanService.getWorkOutPlanById(id));
        model.addAttribute("users", userService.getAllUsers());
        return "addupdateworkoutplans";
    }
}
