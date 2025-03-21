/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.personalmanagement;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author nguyenho
 */
public class PersonalManagementUtils {

    private static final SessionFactory FACTORY;

    static {
        Configuration conf = new Configuration();

        Properties pros = new Properties();
        pros.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        pros.put(Environment.JAKARTA_JDBC_URL, "jdbc:mysql://localhost/persondb");
        pros.put(Environment.JAKARTA_JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
        pros.put(Environment.JAKARTA_JDBC_USER, "root");
        pros.put(Environment.JAKARTA_JDBC_PASSWORD, "Admin@123");
        pros.put(Environment.SHOW_SQL, "true");
        conf.setProperties(pros);

//        conf.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory(serviceRegistry);
    }

    /**
     * @return the FACTORY
     */
    public static SessionFactory getFACTORY() {
        return FACTORY;
    }
}
