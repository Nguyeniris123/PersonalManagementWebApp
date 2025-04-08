/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.repositories.impl;

import com.nhom20.pojo.UserAccount;
import com.nhom20.pojo.WorkoutPlan;
import com.nhom20.repositories.WorkoutPlanRepository;
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
public class WorkoutPlanRepositoryImpl implements WorkoutPlanRepository {

    private static final int PAGE_SIZE = 6;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<WorkoutPlan> getWorkOutPlan(Map<String, String> params) {

        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<WorkoutPlan> q = b.createQuery(WorkoutPlan.class);
        Root<WorkoutPlan> root = q.from(WorkoutPlan.class);
        Join<WorkoutPlan, UserAccount> userJoin = root.join("userId", JoinType.INNER);  // Thêm Join với bảng UserAccount
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            // Tìm kiếm theo target
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
    public WorkoutPlan getWorkOutPlanById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(WorkoutPlan.class, id);
    }

    @Override
    public WorkoutPlan addOrUpdategetWorkOutPlan(WorkoutPlan workoutPlan) {
        Session s = this.factory.getObject().getCurrentSession();
        if (workoutPlan.getId() == null) {
            s.persist(workoutPlan);
        } else {
            s.merge(workoutPlan);
        }
        return workoutPlan;
    }

    @Override
    public void deletegetWorkOutPlan(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        WorkoutPlan wp = this.getWorkOutPlanById(id);
        if (wp != null) {
            s.evict(wp.getUserId()); // tách đối tượng UserAccount khỏi session
            s.remove(wp);
        }
    }
}
