package com.nhom20.controllers;

import com.nhom20.services.StatisticsService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author nguyenho
 */

@Controller
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/statistics")
    public String stats(
            @RequestParam(value = "userId", required = false, defaultValue = "1") int userId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        if (startDate == null || endDate == null) {
            LocalDate now = LocalDate.now();
            if (startDate == null) {
                startDate = now.minusMonths(1);
            }
            if (endDate == null) {
                endDate = now;
            }
        }

        List<Object[]> weeklyStats = statisticsService.statsByWeek(userId, startDate, endDate);
        List<Object[]> monthlyStats = statisticsService.statsByMonth(userId, startDate, endDate);

        int totalDuration = weeklyStats.stream()
                .mapToInt(o -> ((Number) o[1]).intValue())
                .sum();

        int totalCalories = weeklyStats.stream()
                .mapToInt(o -> ((Number) o[2]).intValue())
                .sum();

        model.addAttribute("weeklyStats", weeklyStats);
        model.addAttribute("monthlyStats", monthlyStats);
        model.addAttribute("totalDuration", totalDuration);
        model.addAttribute("totalCalories", totalCalories);

        model.addAttribute("userId", userId);
        model.addAttribute("startDate", startDate.format(DateTimeFormatter.ISO_DATE));
        model.addAttribute("endDate", endDate.format(DateTimeFormatter.ISO_DATE));

        return "statistics";
    }
}
