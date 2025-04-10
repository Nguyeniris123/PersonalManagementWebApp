/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.services.impl;

import com.nhom20.pojo.Exercise;
import com.nhom20.repositories.ExerciseRepository;
import com.nhom20.services.ExerciseService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguyenho
 */

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;
    
    @Override
    public List<Exercise> getExercise(Map<String, String> params) {
        return this.exerciseRepository.getExercise(params);
    }

    @Override
    public Exercise getExerciseById(int id) {
        return this.exerciseRepository.getExerciseById(id);
    }

    @Override
    public boolean addOrUpdateExercise(Exercise exercise) {
        return this.exerciseRepository.addOrUpdateExercise(exercise) != null;
    }

    @Override
    public boolean deleteExercise(int id) {
        try {
            this.exerciseRepository.deleteExercise(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
