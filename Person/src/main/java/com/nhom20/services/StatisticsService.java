package com.nhom20.services;

import com.nhom20.pojo.HealthStatistics;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    public Map<String, Object> getStatisticsData(int userId, String period, String startDate, String endDate) {
        // TODO: Implement actual database query
        List<HealthStatistics> statistics = getMockData(userId);
        
        Map<String, Object> result = new HashMap<>();
        
        // Filter data based on period
        List<HealthStatistics> filteredData = filterDataByPeriod(statistics, period, startDate, endDate);
        
        // Calculate statistics
        result.put("exerciseDuration", calculateExerciseDuration(filteredData));
        result.put("caloriesBurned", calculateCaloriesBurned(filteredData));
        result.put("waterIntake", calculateWaterIntake(filteredData));
        result.put("weight", calculateWeight(filteredData));
        result.put("bmi", calculateBMI(filteredData));
        
        // Prepare data for charts
        result.put("chartData", prepareChartData(filteredData));
        
        return result;
    }

    private List<HealthStatistics> filterDataByPeriod(List<HealthStatistics> data, String period, String startDate, String endDate) {
        LocalDateTime start = startDate != null ? 
            LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME) :
            LocalDateTime.now().minusDays(period.equals("week") ? 7 : period.equals("month") ? 30 : 365);
            
        LocalDateTime end = endDate != null ?
            LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME) :
            LocalDateTime.now();

        return data.stream()
            .filter(stat -> stat.getDate().isAfter(start) && stat.getDate().isBefore(end))
            .collect(Collectors.toList());
    }

    private Map<String, Object> prepareChartData(List<HealthStatistics> data) {
        Map<String, Object> chartData = new HashMap<>();
        
        // Prepare labels (dates)
        List<String> labels = data.stream()
            .map(stat -> stat.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .distinct()
            .collect(Collectors.toList());
        
        // Prepare datasets
        List<Double> exerciseDuration = data.stream()
            .map(HealthStatistics::getExerciseDuration)
            .collect(Collectors.toList());
            
        List<Double> caloriesBurned = data.stream()
            .map(HealthStatistics::getCaloriesBurned)
            .collect(Collectors.toList());
            
        List<Double> waterIntake = data.stream()
            .map(HealthStatistics::getWaterIntake)
            .collect(Collectors.toList());
            
        List<Double> weight = data.stream()
            .map(HealthStatistics::getWeight)
            .collect(Collectors.toList());
            
        List<Double> bmi = data.stream()
            .map(HealthStatistics::getBmi)
            .collect(Collectors.toList());
        
        chartData.put("labels", labels);
        chartData.put("exerciseDuration", exerciseDuration);
        chartData.put("caloriesBurned", caloriesBurned);
        chartData.put("waterIntake", waterIntake);
        chartData.put("weight", weight);
        chartData.put("bmi", bmi);
        
        return chartData;
    }

    private double calculateExerciseDuration(List<HealthStatistics> data) {
        return data.stream()
            .mapToDouble(HealthStatistics::getExerciseDuration)
            .sum();
    }

    private double calculateCaloriesBurned(List<HealthStatistics> data) {
        return data.stream()
            .mapToDouble(HealthStatistics::getCaloriesBurned)
            .sum();
    }

    private double calculateWaterIntake(List<HealthStatistics> data) {
        return data.stream()
            .mapToDouble(HealthStatistics::getWaterIntake)
            .average()
            .orElse(0.0);
    }

    private double calculateWeight(List<HealthStatistics> data) {
        return data.stream()
            .mapToDouble(HealthStatistics::getWeight)
            .average()
            .orElse(0.0);
    }

    private double calculateBMI(List<HealthStatistics> data) {
        return data.stream()
            .mapToDouble(HealthStatistics::getBmi)
            .average()
            .orElse(0.0);
    }

    // Mock data for testing
    private List<HealthStatistics> getMockData(int userId) {
        List<HealthStatistics> data = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (int i = 0; i < 30; i++) {
            HealthStatistics stat = new HealthStatistics();
            stat.setUserId(userId);
            stat.setDate(now.minusDays(i));
            stat.setExerciseDuration(30 + Math.random() * 30);
            stat.setCaloriesBurned(200 + Math.random() * 300);
            stat.setWaterIntake(1500 + Math.random() * 500);
            stat.setWeight(70 + Math.random() * 5);
            stat.setBmi(22 + Math.random() * 2);
            stat.setExerciseType("Running");
            data.add(stat);
        }
        
        return data;
    }
} 