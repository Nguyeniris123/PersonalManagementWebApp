package com.nhom20.repositories.impl;

import com.nhom20.pojo.Exercise;
import com.nhom20.pojo.WorkoutPlanExercise;
import com.nhom20.repositories.StatisticsRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.*;

@Repository
@Transactional
public class StatisticsRepositoryImpl implements StatisticsRepository {
    private final DataSource dataSource;

    @Autowired
    public StatisticsRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Map<String, Object>> getWeeklyStatistics(Date startDate, Date endDate) {
        List<Map<String, Object>> statistics = new ArrayList<>();
        String sql = "SELECT DATE(wpe.created_at) as date, " +
                "SUM(wpe.duration_minutes) as total_time, " +
                "SUM(e.calories_burned * wpe.duration_minutes / 60) as total_calories " +
                "FROM workout_plan_exercise wpe " +
                "JOIN exercise e ON wpe.exercise_id = e.id " +
                "WHERE wpe.created_at BETWEEN ? AND ? " +
                "GROUP BY DATE(wpe.created_at) " +
                "ORDER BY date";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setDate(1, new java.sql.Date(startDate.getTime()));
            stm.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Map<String, Object> stat = new HashMap<>();
                stat.put("date", rs.getDate("date"));
                stat.put("totalExerciseTime", rs.getDouble("total_time"));
                stat.put("totalCaloriesBurned", rs.getDouble("total_calories"));
                statistics.add(stat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statistics;
    }

    @Override
    public List<Map<String, Object>> getMonthlyStatistics(Date startDate, Date endDate) {
        List<Map<String, Object>> statistics = new ArrayList<>();
        String sql = "SELECT DATE_TRUNC('month', wpe.created_at) as date, " +
                "SUM(wpe.duration_minutes) as total_time, " +
                "SUM(e.calories_burned * wpe.duration_minutes / 60) as total_calories " +
                "FROM workout_plan_exercise wpe " +
                "JOIN exercise e ON wpe.exercise_id = e.id " +
                "WHERE wpe.created_at BETWEEN ? AND ? " +
                "GROUP BY DATE_TRUNC('month', wpe.created_at) " +
                "ORDER BY date";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setDate(1, new java.sql.Date(startDate.getTime()));
            stm.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Map<String, Object> stat = new HashMap<>();
                stat.put("date", rs.getDate("date"));
                stat.put("totalExerciseTime", rs.getDouble("total_time"));
                stat.put("totalCaloriesBurned", rs.getDouble("total_calories"));
                statistics.add(stat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statistics;
    }

    @Override
    public double getTotalExerciseTime(Date startDate, Date endDate) {
        String sql = "SELECT SUM(wpe.duration_minutes) as total_time " +
                "FROM workout_plan_exercise wpe " +
                "WHERE wpe.created_at BETWEEN ? AND ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setDate(1, new java.sql.Date(startDate.getTime()));
            stm.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_time");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public double getTotalCaloriesBurned(Date startDate, Date endDate) {
        String sql = "SELECT SUM(e.calories_burned * wpe.duration_minutes / 60) as total_calories " +
                "FROM workout_plan_exercise wpe " +
                "JOIN exercise e ON wpe.exercise_id = e.id " +
                "WHERE wpe.created_at BETWEEN ? AND ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setDate(1, new java.sql.Date(startDate.getTime()));
            stm.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_calories");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
