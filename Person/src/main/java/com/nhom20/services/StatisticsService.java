package com.nhom20.services;

import com.nhom20.pojo.Statistics;
import java.util.Date;
import java.util.List;

public interface StatisticsService {
    int getTotalExerciseTime(int userId, Date startDate, Date endDate);
    int getTotalCaloriesBurned(int userId, Date startDate, Date endDate);
    List<Statistics> getStatisticsDetails(int userId, Date startDate, Date endDate);
} 