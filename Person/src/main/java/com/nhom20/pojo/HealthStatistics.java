package com.nhom20.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HealthStatistics implements Serializable {
    private int id;
    private int userId;
    private LocalDateTime date;
    private double exerciseDuration; // in minutes
    private double caloriesBurned;
    private double waterIntake; // in ml
    private double weight; // in kg
    private double bmi;
    private String exerciseType;

    public HealthStatistics() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(double exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public double getWaterIntake() {
        return waterIntake;
    }

    public void setWaterIntake(double waterIntake) {
        this.waterIntake = waterIntake;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }
} 