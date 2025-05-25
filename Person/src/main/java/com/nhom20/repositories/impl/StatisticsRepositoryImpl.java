package com.nhom20.repositories.impl;

import com.nhom20.pojo.Exercise;
import com.nhom20.pojo.WorkoutPlan;
import com.nhom20.pojo.WorkoutPlanExercise;
import com.nhom20.repositories.StatisticsRepository;
import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StatisticsRepositoryImpl implements StatisticsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> statsByWeek(int userId, LocalDate startDate, LocalDate endDate) {
        Session session = factory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<WorkoutPlanExercise> wpeRoot = cq.from(WorkoutPlanExercise.class);
        Join<WorkoutPlanExercise, WorkoutPlan> wpJoin = wpeRoot.join("workoutPlanId");
        Join<WorkoutPlanExercise, Exercise> eJoin = wpeRoot.join("exerciseId");

        Expression<Integer> weekExp = cb.function("WEEK", Integer.class, wpJoin.get("startDate"));

        Predicate userPredicate = cb.equal(wpJoin.get("userId").get("id"), userId);
        Predicate startPredicate = cb.greaterThanOrEqualTo(wpJoin.get("startDate"), java.sql.Date.valueOf(startDate));
        Predicate endPredicate = cb.lessThanOrEqualTo(wpJoin.get("startDate"), java.sql.Date.valueOf(endDate));

        cq.multiselect(
                weekExp,
                cb.sum(wpeRoot.get("durationMinutes")),
                cb.sum(cb.prod(wpeRoot.get("sets"), eJoin.get("caloriesBurned")))
        );

        cq.where(cb.and(userPredicate, startPredicate, endPredicate));
        cq.groupBy(weekExp);
        cq.orderBy(cb.asc(weekExp));

        return session.createQuery(cq).getResultList();
    }

    @Override
    public List<Object[]> statsByMonth(int userId, LocalDate startDate, LocalDate endDate) {
        Session session = factory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<WorkoutPlanExercise> wpeRoot = cq.from(WorkoutPlanExercise.class);
        Join<WorkoutPlanExercise, WorkoutPlan> wpJoin = wpeRoot.join("workoutPlanId");
        Join<WorkoutPlanExercise, Exercise> eJoin = wpeRoot.join("exerciseId");

        Expression<Integer> monthExp = cb.function("MONTH", Integer.class, wpJoin.get("startDate"));

        Predicate userPredicate = cb.equal(wpJoin.get("userId").get("id"), userId);
        Predicate startPredicate = cb.greaterThanOrEqualTo(wpJoin.get("startDate"), java.sql.Date.valueOf(startDate));
        Predicate endPredicate = cb.lessThanOrEqualTo(wpJoin.get("startDate"), java.sql.Date.valueOf(endDate));

        cq.multiselect(
                monthExp,
                cb.sum(wpeRoot.get("durationMinutes")),
                cb.sum(cb.prod(wpeRoot.get("sets"), eJoin.get("caloriesBurned")))
        );

        cq.where(cb.and(userPredicate, startPredicate, endPredicate));
        cq.groupBy(monthExp);
        cq.orderBy(cb.asc(monthExp));

        return session.createQuery(cq).getResultList();
    }
}
