/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.repositories.impl;

import com.nhom20.pojo.UserAccount;
import com.nhom20.repositories.UserRepository;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 *
 * @author nguyenho
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public UserAccount getUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(UserAccount.class, id);
    }

    @Override
    public UserAccount getUserByUsername(String username) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<UserAccount> query = session.createNamedQuery("UserAccount.findByUsername", UserAccount.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;  // Trả về null nếu không tìm thấy
        }
    }

    @Override
    public List<UserAccount> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        Query<UserAccount> query = session.createNamedQuery("UserAccount.findAll", UserAccount.class);
        return query.getResultList();
    }

    @Override
    public List<UserAccount> searchUsersByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<UserAccount> query = session.createQuery("FROM UserAccount u WHERE u.username LIKE :username", UserAccount.class);
        query.setParameter("username", "%" + username + "%");
        return query.getResultList();
    }

    @Override
    public UserAccount addUser(UserAccount user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
        return user;
    }

    @Override
    public UserAccount updateUser(UserAccount user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
        return user;
    }

    @Override
    public boolean deleteUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        UserAccount user = session.get(UserAccount.class, id);
        if (user != null) {
            session.delete(user);
            return true;
        }
        return false;
    }
}
