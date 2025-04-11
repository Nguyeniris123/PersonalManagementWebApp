/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.services.ExerciseService;
import com.nhom20.services.WorkoutPlanExerciseService;
import com.nhom20.services.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
    
    
    
    
}
