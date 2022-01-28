package com.htwsaar.server;

import com.htwsaar.server.hibernate.dao.UserDao;
import com.htwsaar.server.hibernate.entity.User;


public class App {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        UserDao userDao = new UserDao();
        User user = new User("Simon", "Mongo");
        User user2 = new User("Test", "PW", 2,2,4);
        userDao.saveUser(user);
        userDao.saveUser(user2);

        User get = userDao.getUser("Test");
        if(get == null) {
            System.out.println("Kein User gefunden");
        } else {
            System.out.println(get.toString());
        }
    }
}
