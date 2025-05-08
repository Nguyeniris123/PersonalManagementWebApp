/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author nguyenho
 */
public class HealthJournal implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private LocalDateTime date;
    private String exerciseType;
    private int duration; // in minutes
    private int caloriesBurned;
    private String mood; // tâm trạng sau khi tập
    private String notes; // ghi chú, cảm nhận
    private int energyLevel; // mức năng lượng (1-10)
    private int difficultyLevel; // độ khó của bài tập (1-10)
    private String achievements; // thành tựu đạt được
    private String challenges; // thách thức gặp phải
    private String nextGoals; // mục tiêu tiếp theo

    public HealthJournal() {
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

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getChallenges() {
        return challenges;
    }

    public void setChallenges(String challenges) {
        this.challenges = challenges;
    }

    public String getNextGoals() {
        return nextGoals;
    }

    public void setNextGoals(String nextGoals) {
        this.nextGoals = nextGoals;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != 0 ? id : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HealthJournal)) {
            return false;
        }
        HealthJournal other = (HealthJournal) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "com.nhom20.pojo.HealthJournal[ id=" + id + " ]";
    }
    
}
