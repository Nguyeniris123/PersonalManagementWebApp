package com.nhom20.controllers;

import com.nhom20.pojo.Statistics;
import com.nhom20.services.StatisticsService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class ApiStatisticsController {
    private StatisticsService statisticsService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ApiStatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/total-exercise-time")
    public int getTotalExerciseTime(
            @RequestParam int userId,
            @RequestParam String startDate,
            @RequestParam String endDate) throws ParseException {
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);
        return statisticsService.getTotalExerciseTime(userId, start, end);
    }

    @GetMapping("/total-calories")
    public int getTotalCaloriesBurned(
            @RequestParam int userId,
            @RequestParam String startDate,
            @RequestParam String endDate) throws ParseException {
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);
        return statisticsService.getTotalCaloriesBurned(userId, start, end);
    }

    @GetMapping("/exercise-details")
    public List<Statistics> getStatisticsDetails(
            @RequestParam int userId,
            @RequestParam String startDate,
            @RequestParam String endDate) throws ParseException {
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);
        return statisticsService.getStatisticsDetails(userId, start, end);
    }
} 