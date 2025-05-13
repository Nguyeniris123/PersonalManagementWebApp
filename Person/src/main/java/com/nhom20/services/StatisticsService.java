package com.nhom20.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticsService {
    List<Map<String, Object>> getWeeklyStatistics(Date startDate, Date endDate);
    List<Map<String, Object>> getMonthlyStatistics(Date startDate, Date endDate);
    double getTotalExerciseTime(Date startDate, Date endDate);
    double getTotalCaloriesBurned(Date startDate, Date endDate);
} 