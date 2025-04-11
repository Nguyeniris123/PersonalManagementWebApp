/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.services.impl;

import com.nhom20.pojo.WorkoutPlanExercise;
import com.nhom20.repositories.WorkoutPlanExerciseRepository;
import com.nhom20.repositories.WorkoutPlanRepository;
import com.nhom20.services.WorkoutPlanExerciseService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguyenho
 */

@Service
public class WorkoutPlanExerciseServiceImpl implements WorkoutPlanExerciseService {
    
    @Autowired
    private WorkoutPlanExerciseRepository workoutPlanExerciseRepository;

    @Override
    public List<WorkoutPlanExercise> getWorkOutPlanExercise(Map<String, String> params) {
        return workoutPlanExerciseRepository.getWorkOutPlanExercise(params);
    }

    @Override
    public WorkoutPlanExercise getWorkoutPlanExerciseById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addOrUpdateWorkOutPlan(WorkoutPlanExercise workoutPlanExercise) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deleteWorkoutPlanExercise(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
