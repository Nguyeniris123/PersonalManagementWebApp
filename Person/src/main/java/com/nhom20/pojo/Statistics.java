package com.nhom20.pojo;

public class Statistics {
    private Integer totalDurationMinutes;
    private Integer totalCaloriesBurned;

    public Statistics(Integer totalDurationMinutes, Integer totalCaloriesBurned) {
        this.totalDurationMinutes = totalDurationMinutes != null ? totalDurationMinutes : 0;
        this.totalCaloriesBurned = totalCaloriesBurned != null ? totalCaloriesBurned : 0;
    }

    /**
     * @return the totalDurationMinutes
     */
    public Integer getTotalDurationMinutes() {
        return totalDurationMinutes;
    }

    /**
     * @param totalDurationMinutes the totalDurationMinutes to set
     */
    public void setTotalDurationMinutes(Integer totalDurationMinutes) {
        this.totalDurationMinutes = totalDurationMinutes;
    }

    /**
     * @return the totalCaloriesBurned
     */
    public Integer getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    /**
     * @param totalCaloriesBurned the totalCaloriesBurned to set
     */
    public void setTotalCaloriesBurned(Integer totalCaloriesBurned) {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    
} 