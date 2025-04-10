/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.Exercise;
import com.nhom20.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author nguyenho
 */

@Controller
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;
    
    @GetMapping("/exercise-view")
    public String addView(Model model) {
        model.addAttribute("exercise", new Exercise());
        return "addupdateexercises";
    }
    
    @PostMapping("/exercises/add")
    public String add(@ModelAttribute(value = "exercise") Exercise e) {
        this.exerciseService.addOrUpdateExercise(e);
        
        return "redirect:/exercises";
    }
    
    @GetMapping("/exercises/{exerciseId}")
    public String updateView(Model model, @PathVariable(value = "exerciseId") int id) {
        model.addAttribute("exercise", this.exerciseService.getExerciseById(id));
        
        return "addupdateexercises";
    }
}
