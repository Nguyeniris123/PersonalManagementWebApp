package com.nhom20.repositories;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsRepository {
    List<Object[]> statsByWeek(int userId, LocalDate startDate, LocalDate endDate);
    List<Object[]> statsByMonth(int userId, LocalDate startDate, LocalDate endDate);
} 