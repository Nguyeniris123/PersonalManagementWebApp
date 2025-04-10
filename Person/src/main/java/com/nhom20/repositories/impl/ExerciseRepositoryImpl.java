/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.repositories.impl;

import com.nhom20.pojo.Exercise;
import com.nhom20.repositories.ExerciseRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
public class ExerciseRepositoryImpl implements ExerciseRepository {
    
    private static final int PAGE_SIZE = 6;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Exercise> getExercise(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Exercise> q = b.createQuery(Exercise.class);
        Root root = q.from(Exercise.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
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
    public Exercise getExerciseById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Exercise.class, id);
    }

    @Override
    public Exercise addOrUpdateExercise(Exercise exercise) {
        Session s = this.factory.getObject().getCurrentSession();
        if (exercise.getId() == null) {
            s.persist(exercise);
        } else {
            s.merge(exercise);
        }

        return exercise;
    }

    @Override
    public void deleteExercise(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Exercise e = this.getExerciseById(id);
        s.remove(e);
    }
    
}
