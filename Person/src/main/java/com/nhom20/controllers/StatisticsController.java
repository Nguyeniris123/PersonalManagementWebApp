package com.nhom20.controllers;

import com.nhom20.services.StatisticsService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private StatisticsService statisticsService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/weekly")
    public List<Map<String, Object>> getWeeklyStatistics(
            @RequestParam String startDate,
            @RequestParam String endDate) throws ParseException {
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);
        return statisticsService.getWeeklyStatistics(start, end);
    }

    @GetMapping("/monthly")
    public List<Map<String, Object>> getMonthlyStatistics(
            @RequestParam String startDate,
            @RequestParam String endDate) throws ParseException {
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);
        return statisticsService.getMonthlyStatistics(start, end);
    }

    @GetMapping("/total-exercise-time")
    public double getTotalExerciseTime(
            @RequestParam String startDate,
            @RequestParam String endDate) throws ParseException {
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);
        return statisticsService.getTotalExerciseTime(start, end);
    }

    @GetMapping("/total-calories")
    public double getTotalCaloriesBurned(
            @RequestParam String startDate,
            @RequestParam String endDate) throws ParseException {
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);
        return statisticsService.getTotalCaloriesBurned(start, end);
    }
} 