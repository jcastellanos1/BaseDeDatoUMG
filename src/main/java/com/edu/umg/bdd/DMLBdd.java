/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.umg.bdd;
/**
 *
 * @author caste
 */
import com.edu.umg.DTO.persona;
import com.edu.umg.cfg.HibernateCfg;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class DMLBdd {
    private static DMLBdd dml;

    // Usamos el SessionFactory desde HibernateUtil
    private static SessionFactory sessionFactory = HibernateCfg.getSessionFactory();

    // Constructor privado para Singleton
    public DMLBdd() {}

    public static DMLBdd getDml() {
        if (dml == null) {
            dml = new DMLBdd();
        }
        return dml;
    }

    // Método para obtener todas las personas
    public List<persona> listaPersona() {
        try (Session session = sessionFactory.openSession()) {
            Query<persona> query = session.createQuery("FROM persona", persona.class);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Método para buscar una persona por nombre y apellido
    public List<persona> buscarPersona(String nombre, String apellido) {
        try (Session session = sessionFactory.openSession()) {
            Query<persona> query = session.createQuery("FROM persona WHERE nombre = :nombre AND apellido = :apellido", persona.class);
            query.setParameter("nombre", nombre);
            query.setParameter("apellido", apellido);

            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Método para insertar una persona
    public void insertarPersona(persona persona) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(persona);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    // Método para modificar una persona
    public void modificarPersona(persona persona) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(persona);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    // Método para eliminar una persona
    public void eliminarPersona(persona persona) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(persona);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
}
