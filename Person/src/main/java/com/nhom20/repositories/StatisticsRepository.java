package com.nhom20.repositories;

import com.nhom20.pojo.Exercise;
import com.nhom20.pojo.WorkoutPlanExercise;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticsRepository {
    List<Map<String, Object>> getWeeklyStatistics(Date startDate, Date endDate);
    List<Map<String, Object>> getMonthlyStatistics(Date startDate, Date endDate);
    double getTotalExerciseTime(Date startDate, Date endDate);
    double getTotalCaloriesBurned(Date startDate, Date endDate);
} 