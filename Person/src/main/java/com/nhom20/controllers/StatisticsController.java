package com.nhom20.controllers;

import com.nhom20.pojo.HealthStatistics;
import com.nhom20.services.StatisticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    public ModelAndView showStatisticsPage(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("statistics");
    }

    @GetMapping("/data")
    @ResponseBody
    public Map<String, Object> getStatisticsData(
            @RequestParam(required = false, defaultValue = "week") String period,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpSession session) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Map.of("error", "Unauthorized");
        }

        try {
            return statisticsService.getStatisticsData(userId, period, startDate, endDate);
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
} 