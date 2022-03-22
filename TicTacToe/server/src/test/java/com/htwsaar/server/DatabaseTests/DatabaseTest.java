package com.htwsaar.server.DatabaseTests;


import com.htwsaar.server.Hibernate.dao.UserDao;
import com.htwsaar.server.Hibernate.entity.User;
import org.junit.Test;


/**
 * DB Tests, müssen manuell getestet werden.
 * DB Einstellungen in HibernateUtils.java ändern auf Lokale Daten
 * Tests aufrufen -> DB öffnen -> schauen, ob die Daten richtig geändert wurden
 * Automatische Tests möglicherweise über DBUnit
 */
public class DatabaseTest {
//    private UserDao userDao = new UserDao();
//
//    @Test
//    public void saveUserTest() {
//        User user = new User("Markus", "Testus");
//        userDao.saveUser(user);
//    }
//
//    @Test
//    public void getUpdateUser() {
//        User user = userDao.getUser("Markus");
//        user.setPassword("NeuesPW");
//        userDao.updateUser(user);
//    }
}
