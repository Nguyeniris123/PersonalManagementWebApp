package com.nhom20.pojo;

import java.util.Date;

public class Statistics {
    private Date date;
    private double totalExerciseTime; // Thời gian tập luyện (phút)
    private double totalCaloriesBurned; // Lượng calo tiêu thụ

    public Statistics() {
    }

    public Statistics(Date date, double totalExerciseTime, double totalCaloriesBurned) {
        this.date = date;
        this.totalExerciseTime = totalExerciseTime;
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    // Getters and Setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotalExerciseTime() {
        return totalExerciseTime;
    }

    public void setTotalExerciseTime(double totalExerciseTime) {
        this.totalExerciseTime = totalExerciseTime;
    }

    public double getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(double totalCaloriesBurned) {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }
} 