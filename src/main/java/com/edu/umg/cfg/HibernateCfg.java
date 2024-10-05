/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.umg.cfg;

/**
 *
 * @author caste
 */
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateCfg {
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://localhost/clasedesarrollo")
                .setProperty("hibernate.connection.username", "root")
                .setProperty("hibernate.connection.password", "1234567")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.show_sql", "true")
                .addAnnotatedClass(com.edu.umg.DTO.persona.class)  // Asegúrate de que la clase esté mapeada correctamente
                .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
