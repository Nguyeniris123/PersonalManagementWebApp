/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.repositories;

import com.nhom20.pojo.WorkoutPlanExercise;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguyenho
 */
public interface WorkoutPlanExerciseRepository {
    List<WorkoutPlanExercise> getWorkOutPlanExercise(Map<String, String> params);
    WorkoutPlanExercise getWorkoutPlanExerciseById(int id);
    WorkoutPlanExercise addOrUpdateWorkOutPlanExercise(WorkoutPlanExercise workoutPlanExercise);
    void deleteWorkoutPlanExercise(int id);
}
