package com.nhom20.services;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author nguyenho
 */

public interface StatisticsService {
    List<Object[]> statsByWeek(int userId, LocalDate startDate, LocalDate endDate);
    List<Object[]> statsByMonth(int userId, LocalDate startDate, LocalDate endDate);
} 