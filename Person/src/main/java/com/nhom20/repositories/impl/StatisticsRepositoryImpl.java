package com.nhom20.repositories.impl;

import com.nhom20.pojo.Exercise;
import com.nhom20.pojo.Statistics;
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
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

@Repository
@Transactional
public class StatisticsRepositoryImpl implements StatisticsRepository {
    private final DataSource dataSource;

    @Autowired
    public StatisticsRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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

    @Override
    public List<Statistics> findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(Integer userId, Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Statistics> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Statistics> findAll(Sort sort) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Statistics> findAllById(Iterable<Integer> ids) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Statistics> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Statistics> S saveAndFlush(S entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Statistics> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAllInBatch(Iterable<Statistics> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> ids) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Statistics getOne(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Statistics getById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Statistics getReferenceById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Statistics> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Statistics> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Page<Statistics> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Statistics> S save(S entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Statistics> findById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean existsById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Statistics entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll(Iterable<? extends Statistics> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Statistics> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Statistics> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Statistics> long count(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Statistics> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Statistics, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
