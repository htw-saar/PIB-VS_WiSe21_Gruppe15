package com.htwsaar.server.Hibernate.dao;

import com.htwsaar.server.Hibernate.entity.User;
import com.htwsaar.server.Hibernate.utils.HibernateUtils;
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

    public List<User> getScoreboard() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("From User ORDER BY score DESC, wins DESC", User.class).list();
        }
    }
}
