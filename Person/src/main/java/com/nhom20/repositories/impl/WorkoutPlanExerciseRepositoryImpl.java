/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.repositories.impl;

import com.nhom20.pojo.Exercise;
import com.nhom20.pojo.WorkoutPlan;
import com.nhom20.pojo.WorkoutPlanExercise;
import com.nhom20.repositories.WorkoutPlanExerciseRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nguyenho
 */
@Repository
@Transactional
public class WorkoutPlanExerciseRepositoryImpl implements WorkoutPlanExerciseRepository {

    private static final int PAGE_SIZE = 6;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<WorkoutPlanExercise> getWorkOutPlanExercise(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<WorkoutPlanExercise> q = b.createQuery(WorkoutPlanExercise.class);
        Root<WorkoutPlanExercise> root = q.from(WorkoutPlanExercise.class);
        Join<WorkoutPlanExercise, WorkoutPlan> workoutPlanJoin = root.join("workoutPlanId", JoinType.INNER);
        Join<WorkoutPlanExercise, Exercise> exerciseJoin = root.join("exerciseId", JoinType.INNER);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate byExerciseName = b.like(exerciseJoin.get("name"), "%" + kw + "%");
                Predicate byPlanName = b.like(workoutPlanJoin.get("name"), "%" + kw + "%");
                predicates.add(b.or(byExerciseName, byPlanName));
            }

            q.where(predicates.toArray(Predicate[]::new));

            String orderBy = params.get("orderBy");
            if (orderBy != null && !orderBy.isEmpty()) {
                q.orderBy(b.asc(root.get(orderBy)));
            }
        }

        Query query = s.createQuery(q);

        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;

            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult(start);
        }
        return query.getResultList();
    }

    @Override
    public WorkoutPlanExercise getWorkoutPlanExerciseById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(WorkoutPlanExercise.class, id);
    }

    @Override
    public WorkoutPlanExercise addOrUpdateWorkOutPlanExercise(WorkoutPlanExercise workoutPlanExercise) {
        Session s = this.factory.getObject().getCurrentSession();
        if (workoutPlanExercise.getId() == null) {
            s.persist(workoutPlanExercise);
        } else {
            s.merge(workoutPlanExercise);
        }
        return workoutPlanExercise;
    }

    @Override
    public void deleteWorkoutPlanExercise(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        WorkoutPlanExercise wpe = this.getWorkoutPlanExerciseById(id);
        if (wpe != null) {
            s.remove(wpe);
        }
    }

    @Override
    public List<WorkoutPlanExercise> getWorkoutPlanExercisesByWorkoutPlanId(int workoutPlanId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<WorkoutPlanExercise> q = b.createQuery(WorkoutPlanExercise.class);
        Root<WorkoutPlanExercise> root = q.from(WorkoutPlanExercise.class);

        q.select(root).where(b.equal(root.get("workoutPlanId").get("id"), workoutPlanId));

        Query query = s.createQuery(q);
        return query.getResultList();
    }
}
