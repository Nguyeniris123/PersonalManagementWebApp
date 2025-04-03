/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.repositories.impl;

import com.nhom20.pojo.HealthProfile;
import com.nhom20.pojo.UserAccount;
import com.nhom20.repositories.HealthProfileRepository;
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
public class HealthProfileRepositoryImpl implements HealthProfileRepository {

    private static final int PAGE_SIZE = 6;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<HealthProfile> getHealthProfiles(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<HealthProfile> q = b.createQuery(HealthProfile.class);
        Root<HealthProfile> root = q.from(HealthProfile.class);
        Join<HealthProfile, UserAccount> userJoin = root.join("userId", JoinType.INNER);  // Thêm Join với bảng UserAccount
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            // Tìm kiếm theo target
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("target"), String.format("%%%s%%", kw)));
            }

            // Tìm kiếm theo BMI
            String minBmi = params.get("minBmi");
            if (minBmi != null && !minBmi.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(root.get("bmi"), Float.valueOf(minBmi)));
            }

            String maxBmi = params.get("maxBmi");
            if (maxBmi != null && !maxBmi.isEmpty()) {
                predicates.add(b.lessThanOrEqualTo(root.get("bmi"), Float.valueOf(maxBmi)));
            }

            // Tìm kiếm theo tên người dùng
            String username = params.get("username");
            if (username != null && !username.isEmpty()) {
                predicates.add(b.like(userJoin.get("username"), String.format("%%%s%%", username)));
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
    public HealthProfile getHealthProfileById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(HealthProfile.class, id);
    }

    @Override
    public HealthProfile addOrUpdateHealthProfile(HealthProfile healthProfile) {
        Session s = this.factory.getObject().getCurrentSession();
        if (healthProfile.getId() == null) {
            s.persist(healthProfile);
        } else {
            s.merge(healthProfile);
        }

        s.refresh(healthProfile);
        return healthProfile;
    }

    @Override
    public void deleteHealthProfile(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        HealthProfile hp = this.getHealthProfileById(id);
        if (hp != null) {
            s.remove(hp);
        }
    }
}
