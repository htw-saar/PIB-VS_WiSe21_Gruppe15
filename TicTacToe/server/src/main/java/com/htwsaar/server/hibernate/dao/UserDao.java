package com.htwsaar.server.hibernate.dao;

import com.htwsaar.server.hibernate.entity.User;
import com.htwsaar.server.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao {
    Transaction transaction = null;

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

    public User getUser(String username) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("From User Where username=" +username, User.class).list().get(0);
        }
    }

    public List<User> getScoreboard() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("From User", User.class).list();
        }
    }
}
