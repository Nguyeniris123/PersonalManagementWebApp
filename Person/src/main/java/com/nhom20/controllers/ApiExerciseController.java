/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.Exercise;
import com.nhom20.services.ExerciseService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nguyenho
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @DeleteMapping("/exercises/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") int id) {
        this.exerciseService.deleteExercise(id);
    }
    
    @GetMapping("/secure/exercises")
    public ResponseEntity<?> getExercises(@RequestParam Map<String, String> params) {
        try {
            List<Exercise> exercises = exerciseService.getExercise(params);
            return ResponseEntity.ok(exercises);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("Lỗi khi lấy danh sách bài tập!");
        }
    }
    
    @PostMapping("/secure/exercises/add")
    public ResponseEntity<?> addExercise(@RequestBody Exercise e) {
        Exercise saved = exerciseService.addOrUpdateExercise(e);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
