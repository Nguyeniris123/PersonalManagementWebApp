package com.nhom20.services.impl;

import com.nhom20.repositories.StatisticsRepository;
import com.nhom20.services.StatisticsService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statsRepo;

    public StatisticsServiceImpl(StatisticsRepository statsRepo) {
        this.statsRepo = statsRepo;
    }

    @Override
    public List<Object[]> statsByWeek(int userId, LocalDate startDate, LocalDate endDate) {
        return statsRepo.statsByWeek(userId, startDate, endDate);
    }

    @Override
    public List<Object[]> statsByMonth(int userId, LocalDate startDate, LocalDate endDate) {
        return statsRepo.statsByMonth(userId, startDate, endDate);
    }
}
