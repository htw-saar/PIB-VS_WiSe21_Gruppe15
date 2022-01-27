package com.htwsaar.server.hibernate.dao;

import com.htwsaar.server.hibernate.entity.User;
import com.htwsaar.server.hibernate.utils.HibernateUtils;
import org.hibernate.Query;
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
            Query query = session.createQuery("SELECT u FROM User u WHERE u.username=:name");
            query.setParameter("name", username);
            List<User> resultList = query.getResultList();
            return resultList.get(0);
        } catch (Exception e){
            //TODO anders bauen
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getScoreboard() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("From User", User.class).list();
        }
    }
}
