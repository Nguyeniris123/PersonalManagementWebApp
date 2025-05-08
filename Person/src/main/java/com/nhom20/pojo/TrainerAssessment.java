package com.nhom20.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TrainerAssessment implements Serializable {
    private int id;
    private int trainerId;
    private int clientId;
    private LocalDateTime assessmentDate;
    private String overallHealth; // Tình trạng sức khỏe tổng quát
    private String fitnessLevel; // Mức độ thể lực
    private String progress; // Tiến độ
    private String recommendations; // Đề xuất điều chỉnh
    private String exercisePlan; // Kế hoạch tập luyện
    private String nutritionAdvice; // Lời khuyên về dinh dưỡng
    private String notes; // Ghi chú thêm
    private int motivationLevel; // Mức độ động lực (1-10)
    private int adherenceLevel; // Mức độ tuân thủ (1-10)
    private String nextGoals; // Mục tiêu tiếp theo

    public TrainerAssessment() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDateTime assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getOverallHealth() {
        return overallHealth;
    }

    public void setOverallHealth(String overallHealth) {
        this.overallHealth = overallHealth;
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }

    public void setFitnessLevel(String fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getExercisePlan() {
        return exercisePlan;
    }

    public void setExercisePlan(String exercisePlan) {
        this.exercisePlan = exercisePlan;
    }

    public String getNutritionAdvice() {
        return nutritionAdvice;
    }

    public void setNutritionAdvice(String nutritionAdvice) {
        this.nutritionAdvice = nutritionAdvice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getMotivationLevel() {
        return motivationLevel;
    }

    public void setMotivationLevel(int motivationLevel) {
        this.motivationLevel = motivationLevel;
    }

    public int getAdherenceLevel() {
        return adherenceLevel;
    }

    public void setAdherenceLevel(int adherenceLevel) {
        this.adherenceLevel = adherenceLevel;
    }

    public String getNextGoals() {
        return nextGoals;
    }

    public void setNextGoals(String nextGoals) {
        this.nextGoals = nextGoals;
    }
} 