package com.nhom20.services.impl;

import com.nhom20.pojo.Statistics;
import com.nhom20.repositories.StatisticsRepository;
import com.nhom20.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public int getTotalExerciseTime(int userId, Date startDate, Date endDate) {
        List<Statistics> stats = statisticsRepository.findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(userId, startDate, endDate);
        return stats.stream().mapToInt(Statistics::getDurationMinutes).sum();
    }

    @Override
    public int getTotalCaloriesBurned(int userId, Date startDate, Date endDate) {
        List<Statistics> stats = statisticsRepository.findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(userId, startDate, endDate);
        return stats.stream().mapToInt(Statistics::getCaloriesBurned).sum();
    }

    @Override
    public List<Statistics> getStatisticsDetails(int userId, Date startDate, Date endDate) {
        return statisticsRepository.findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(userId, startDate, endDate);
    }
} 