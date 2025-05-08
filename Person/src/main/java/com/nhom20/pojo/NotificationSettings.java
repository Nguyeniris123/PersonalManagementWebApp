package com.nhom20.pojo;

import java.io.Serializable;

public class NotificationSettings implements Serializable {
    private int userId;
    private boolean waterReminderEnabled;
    private boolean exerciseReminderEnabled;
    private boolean restReminderEnabled;
    private int waterReminderInterval; // in minutes
    private int exerciseReminderInterval; // in minutes
    private int restReminderInterval; // in minutes

    public NotificationSettings() {
        // Default values
        this.waterReminderEnabled = true;
        this.exerciseReminderEnabled = true;
        this.restReminderEnabled = true;
        this.waterReminderInterval = 60; // 1 hour
        this.exerciseReminderInterval = 120; // 2 hours
        this.restReminderInterval = 180; // 3 hours
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isWaterReminderEnabled() {
        return waterReminderEnabled;
    }

    public void setWaterReminderEnabled(boolean waterReminderEnabled) {
        this.waterReminderEnabled = waterReminderEnabled;
    }

    public boolean isExerciseReminderEnabled() {
        return exerciseReminderEnabled;
    }

    public void setExerciseReminderEnabled(boolean exerciseReminderEnabled) {
        this.exerciseReminderEnabled = exerciseReminderEnabled;
    }

    public boolean isRestReminderEnabled() {
        return restReminderEnabled;
    }

    public void setRestReminderEnabled(boolean restReminderEnabled) {
        this.restReminderEnabled = restReminderEnabled;
    }

    public int getWaterReminderInterval() {
        return waterReminderInterval;
    }

    public void setWaterReminderInterval(int waterReminderInterval) {
        this.waterReminderInterval = waterReminderInterval;
    }

    public int getExerciseReminderInterval() {
        return exerciseReminderInterval;
    }

    public void setExerciseReminderInterval(int exerciseReminderInterval) {
        this.exerciseReminderInterval = exerciseReminderInterval;
    }

    public int getRestReminderInterval() {
        return restReminderInterval;
    }

    public void setRestReminderInterval(int restReminderInterval) {
        this.restReminderInterval = restReminderInterval;
    }
} 