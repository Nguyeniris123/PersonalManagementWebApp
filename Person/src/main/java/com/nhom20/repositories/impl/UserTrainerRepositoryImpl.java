/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.repositories.impl;

import com.nhom20.pojo.UserAccount;
import com.nhom20.pojo.UserTrainer;
import com.nhom20.repositories.UserTrainerRepository;
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
public class UserTrainerRepositoryImpl implements UserTrainerRepository {

    private static final int PAGE_SIZE = 6;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<UserTrainer> getUserTrainer(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<UserTrainer> q = b.createQuery(UserTrainer.class);
        Root<UserTrainer> root = q.from(UserTrainer.class);

        // Join tới bảng user_account để lấy tên user và trainer
        Join<UserTrainer, UserAccount> userJoin = root.join("userId", JoinType.INNER);
        Join<UserTrainer, UserAccount> trainerJoin = root.join("trainerId", JoinType.INNER);

        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            // Tìm kiếm theo tên người dùng hoặc tên huấn luyện viên
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate byUserName = b.like(userJoin.get("username"), "%" + kw + "%");
                Predicate byTrainerName = b.like(trainerJoin.get("username"), "%" + kw + "%");
                predicates.add(b.or(byUserName, byTrainerName));
            }

            // Lọc theo trạng thái
            String status = params.get("status");
            if (status != null && !status.isEmpty()) {
                predicates.add(b.equal(root.get("status"), status));
            }

            q.where(predicates.toArray(Predicate[]::new));

            // Sắp xếp
            String orderBy = params.get("orderBy");
            if (orderBy != null && !orderBy.isEmpty()) {
                q.orderBy(b.asc(root.get(orderBy)));
            }
        }

        Query query = s.createQuery(q);

        // Phân trang
        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;
            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult(start);
        }
        return query.getResultList();
    }

    @Override
    public UserTrainer getUserTrainerById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(UserTrainer.class, id);
    }

    @Override
    public UserTrainer addOrUpdateUserTrainer(UserTrainer userTrainer) {
        Session s = this.factory.getObject().getCurrentSession();

        // Kiểm tra nếu là thêm mới
        if (userTrainer.getId() == null) {
            String hql = "SELECT ut FROM UserTrainer ut WHERE ut.userId.id = :userId";
            UserTrainer existing = (UserTrainer) s.createQuery(hql)
                    .setParameter("userId", userTrainer.getUserId().getId())
                    .uniqueResult();

            if (existing != null) {
                throw new RuntimeException("Người dùng này đã có huấn luyện viên!");
            }

            s.persist(userTrainer);
        } else {
            // Kiểm tra nếu người dùng có huấn luyện viên khác khi đang cập nhật
            String hql = "SELECT ut FROM UserTrainer ut WHERE ut.userId.id = :userId AND ut.id != :id";
            UserTrainer existing = (UserTrainer) s.createQuery(hql)
                    .setParameter("userId", userTrainer.getUserId().getId())
                    .setParameter("id", userTrainer.getId()) // Exclude current trainer
                    .uniqueResult();

            if (existing != null) {
                throw new RuntimeException("Người dùng này đã có huấn luyện viên khác!");
            }
            s.merge(userTrainer);
        }
        return userTrainer;
    }

    @Override
    public void deleteUserTrainer(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        UserTrainer us = this.getUserTrainerById(id);
        if (us != null) {
            s.remove(us);
        }
    }

    @Override
    public List<UserTrainer> getUserTrainerByUserId(int userId) {
        Session s = this.factory.getObject().getCurrentSession();

        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<UserTrainer> q = b.createQuery(UserTrainer.class);
        Root<UserTrainer> root = q.from(UserTrainer.class);

        q.select(root);

        // Điều kiện lọc theo userId
        Predicate predicate = b.equal(root.get("userId").get("id"), userId);
        q.where(predicate);

        Query query = s.createQuery(q);
        return query.getResultList();
    }
}
