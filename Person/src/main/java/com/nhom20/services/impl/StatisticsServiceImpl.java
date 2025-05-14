package com.nhom20.services.impl;

import com.nhom20.repositories.StatisticsRepository;
import com.nhom20.services.StatisticsService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private StatisticsRepository statisticsRepository;

    public StatisticsServiceImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public List<Map<String, Object>> getWeeklyStatistics(Date startDate, Date endDate) {
        return statisticsRepository.getWeeklyStatistics(startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getMonthlyStatistics(Date startDate, Date endDate) {
        return statisticsRepository.getMonthlyStatistics(startDate, endDate);
    }

    @Override
    public double getTotalExerciseTime(Date startDate, Date endDate) {
        return statisticsRepository.getTotalExerciseTime(startDate, endDate);
    }

    @Override
    public double getTotalCaloriesBurned(Date startDate, Date endDate) {
        return statisticsRepository.getTotalCaloriesBurned(startDate, endDate);
    }
} 