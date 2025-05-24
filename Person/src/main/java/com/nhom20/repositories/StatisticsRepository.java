package com.nhom20.repositories;

import com.nhom20.pojo.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {
    List<Statistics> findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(Integer userId, Date startDate, Date endDate);
} 