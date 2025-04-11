/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.services;

import com.nhom20.pojo.WorkoutPlanExercise;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguyenho
 */
public interface WorkoutPlanExerciseService {
    List<WorkoutPlanExercise> getWorkOutPlanExercise(Map<String, String> params);
    WorkoutPlanExercise getWorkoutPlanExerciseById(int id);
    boolean addOrUpdateWorkOutPlan(WorkoutPlanExercise workoutPlanExercise);
    boolean deleteWorkoutPlanExercise(int id);
}
