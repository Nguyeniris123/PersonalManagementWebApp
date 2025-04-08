/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.repositories;

import com.nhom20.pojo.WorkoutPlan;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguyenho
 */
public interface WorkoutPlanRepository {
    List<WorkoutPlan> getWorkOutPlan(Map<String, String> params);
    WorkoutPlan getWorkOutPlanById(int id);
    WorkoutPlan addOrUpdategetWorkOutPlan(WorkoutPlan workoutPlan);
    void deletegetWorkOutPlan(int id);
}
