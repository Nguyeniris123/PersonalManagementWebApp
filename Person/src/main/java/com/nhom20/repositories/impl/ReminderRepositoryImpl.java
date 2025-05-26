/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.repositories.impl;

import com.nhom20.pojo.Reminder;
import com.nhom20.pojo.UserAccount;
import com.nhom20.repositories.ReminderRepository;
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
import org.hibernate.query.Query;

/**
 *
 * @author nguyenho
 */
@Repository
@Transactional
public class ReminderRepositoryImpl implements ReminderRepository {

    private static final int PAGE_SIZE = 6;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Reminder> getReminder(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Reminder> q = b.createQuery(Reminder.class);
        Root<Reminder> root = q.from(Reminder.class);
        Join<Reminder, UserAccount> userJoin = root.join("userId", JoinType.INNER);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("title"), String.format("%%%s%%", kw)));
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
    public Reminder getReminderById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Reminder.class, id);
    }

    @Override
    public Reminder addOrUpdateReminder(Reminder reminder) {
        Session s = this.factory.getObject().getCurrentSession();
        if (reminder.getId() == null) {
            s.persist(reminder);
        } else {
            s.merge(reminder);
        }
        return reminder;
    }

    @Override
    public void deleteReminder(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Reminder r = this.getReminderById(id);
        if (r != null) {
            s.remove(r);
        }
    }

    @Override
    public List<Reminder> getReminderByUserId(int userId, Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Reminder> q = b.createQuery(Reminder.class);
        Root<Reminder> root = q.from(Reminder.class);
        q.select(root);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(root.get("userId").get("id"), userId));

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("title"), String.format("%%%s%%", kw)));
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
    public List<Reminder> getAllActiveReminders() {
        Session session = this.factory.getObject().getCurrentSession();
        return session.createQuery("FROM Reminder r WHERE r.isActive = true", Reminder.class)
                .getResultList();
    }

}
