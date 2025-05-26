/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.repositories.impl;

import com.nhom20.pojo.HealthJournal;
import com.nhom20.pojo.UserAccount;
import com.nhom20.repositories.HealthJournalRepository;
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
public class HealthJournalRepositoryImpl implements HealthJournalRepository {

    private static final int PAGE_SIZE = 6;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<HealthJournal> getHealthJournal(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<HealthJournal> q = b.createQuery(HealthJournal.class);
        Root<HealthJournal> root = q.from(HealthJournal.class);
        Join<HealthJournal, UserAccount> userJoin = root.join("userId", JoinType.INNER);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("content"), String.format("%%%s%%", kw)));
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
    public HealthJournal getHealthJournalById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(HealthJournal.class, id);
    }

    @Override
    public HealthJournal addOrUpdateHealthJournal(HealthJournal healthJournal) {
        Session s = this.factory.getObject().getCurrentSession();
        if (healthJournal.getId() == null) {
            s.persist(healthJournal);
        } else {
            s.merge(healthJournal);
        }
        return healthJournal;
    }

    @Override
    public void deleteHealthJournal(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        HealthJournal hj = this.getHealthJournalById(id);
        if (hj != null) {
            s.remove(hj);
        }
    }

    @Override
    public List<HealthJournal> getHealthJournalByUserId(int userId, Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<HealthJournal> q = b.createQuery(HealthJournal.class);
        Root<HealthJournal> root = q.from(HealthJournal.class);
        q.select(root);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(root.get("userId").get("id"), userId)); // Luôn lọc theo user

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("content"), String.format("%%%s%%", kw)));
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
}
