/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.WorkoutPlanExercise;
import com.nhom20.services.ExerciseService;
import com.nhom20.services.WorkoutPlanExerciseService;
import com.nhom20.services.WorkoutPlanService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nguyenho
 */
@Controller
public class WorkoutPlanExerciseController {

    @Autowired
    private WorkoutPlanExerciseService workoutPlanExerciseService;

    @Autowired
    private WorkoutPlanService workoutPlanService;

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/workout-plans-exercise-view")
    public String addView(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("workoutPlanExercise", new WorkoutPlanExercise());
        model.addAttribute("workoutPlans", workoutPlanService.getWorkOutPlan(params));
        model.addAttribute("exercises", exerciseService.getExercise(params));
        return "addupdateworkoutplanexercise";
    }

    @PostMapping("workout-plans-exercise/add")
    public String addWorkoutPlan(@ModelAttribute("workoutPlanExercise") WorkoutPlanExercise wpe, Model model, @RequestParam Map<String, String> params) {
        try {
            workoutPlanExerciseService.addOrUpdateWorkOutPlanExercise(wpe);
            return "redirect:/workout-plans-exercise";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("workoutPlans", workoutPlanService.getWorkOutPlan(params));
            model.addAttribute("exercises", exerciseService.getExercise(params));
            return "addupdateworkoutplanexercise";
        }
    }

    @GetMapping("/workout-plans-exercise/{id}")
    public String updateView(Model model, @PathVariable(value = "id") int id, @RequestParam Map<String, String> params) {
        model.addAttribute("workoutPlanExercise", this.workoutPlanExerciseService.getWorkoutPlanExerciseById(id));
        model.addAttribute("workoutPlans", workoutPlanService.getWorkOutPlan(params));
        model.addAttribute("exercises", exerciseService.getExercise(params));
        return "addupdateworkoutplanexercise";
    }

}
