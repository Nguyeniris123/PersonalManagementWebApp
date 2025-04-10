/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.repositories;

import com.nhom20.pojo.Exercise;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguyenho
 */
public interface ExerciseRepository {
    List<Exercise> getExercise(Map<String, String> params);
    Exercise getExerciseById(int id);
    Exercise addOrUpdateExercise(Exercise exercise);
    void deleteExercise(int id);
}
