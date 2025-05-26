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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author nguyenho
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
            return null;
        }
    }
    
    @Override
    public UserAccount getUserByEmail(String email) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<UserAccount> query = session.createNamedQuery("UserAccount.findByEmail", UserAccount.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
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
        session.merge(user);
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

    @Override
    public boolean authenticate(String username, String password) {
        UserAccount u = this.getUserByUsername(username);

        return this.passwordEncoder.matches(password, u.getPassword());
    }
    
    @Override
    public List<UserAccount> findByRole(String role) {
        Session session = sessionFactory.getCurrentSession();
        Query<UserAccount> query = session.createNamedQuery("UserAccount.findByRole", UserAccount.class);
        query.setParameter("role", role);
        return query.getResultList();
    }
}
