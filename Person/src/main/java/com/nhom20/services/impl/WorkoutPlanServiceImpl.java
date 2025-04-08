/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.services.impl;

import com.nhom20.pojo.WorkoutPlan;
import com.nhom20.repositories.WorkoutPlanRepository;
import com.nhom20.services.WorkoutPlanService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguyenho
 */

@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;
    
    @Override
    public List<WorkoutPlan> getWorkOutPlan(Map<String, String> params) {
        return workoutPlanRepository.getWorkOutPlan(params);
    }

    @Override
    public WorkoutPlan getWorkOutPlanById(int id) {
        return workoutPlanRepository.getWorkOutPlanById(id);
    }

    @Override
    public boolean addOrUpdategetWorkOutPlan(WorkoutPlan workoutPlan) {
        return workoutPlanRepository.addOrUpdategetWorkOutPlan(workoutPlan) != null;
    }

    @Override
    public boolean deletegetWorkOutPlan(int id) {
        try {
            this.workoutPlanRepository.deletegetWorkOutPlan(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
