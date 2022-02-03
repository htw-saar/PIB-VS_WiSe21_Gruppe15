package com.htwsaar.server.Hibernate.dao;

import com.htwsaar.server.Hibernate.entity.User;
import com.htwsaar.server.Hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Dao Klasse die Methoden enthält, um Datenbank Änderungen zu vereinfachen
 */
public class UserDao {
    Transaction transaction = null;

    /**
     * Methode um einen neuen Nutzer zu speichern
     *
     * @param user Ein Nutzer, der in die Datenbank gespeichert werden soll
     */
    public void saveUser(User user) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Methode um einen vorhandenen Nutzer zu bearbeiten
     *
     * @param user Ein Nutzer, der bereits in der Datenbank ist und geupdated werden soll
     */
    public void updateUser(User user) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Methode um einen User aus der Datenbank zu erhalten mit einem gegebenen Username
     *
     * @param username Ein Username, der in der Datenbank gesucht werden soll
     * @return User bei einem gefundenen User, falls kein User gefunden wurde, wird null zurückgegeben
     */
    public User getUser(String username) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//            Query query = session.createQuery("SELECT u FROM User u WHERE u.username=:name");
//            query.setParameter("name", username);
//            List<User> resultList = query.getResultList();
//            return resultList.get(0);
            return (User) session.createQuery("SELECT u FROM User u WHERE u.username=:name").setParameter("name", username).getResultList().get(0);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Methode um alle User zu erhalten
     *
     * @return List<User> gibt alle User in der Datenbank als Liste aus
     */
    public List<User> getScoreboard() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("From User ORDER BY score DESC, wins DESC", User.class).list();
        }
    }
}
