package com.htwsaar.server;

import com.htwsaar.server.hibernate.dao.UserDao;
import com.htwsaar.server.hibernate.entity.User;

import java.util.List;

public class App {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        UserDao userDao = new UserDao();
//        User user = new User("Simon", "Mongo");
//        User user2 = new User("Test", "Mongo", 2,2,4);
//        userDao.saveUser(user);
//        userDao.saveUser(user2);
        System.out.println(userDao.getUser("Simon").toString());

//        List<User> scoreboard = userDao.getScoreboard();
//        scoreboard.forEach(users -> System.out.println(users.toString()));

//        DatabaseService databaseService = new DatabaseService();
//        databaseService.addUser("Simon", "test");
//        databaseService.addUser("Alex", "test");

//         databaseService.addWins(1);
//         databaseService.addLoses(1);
//        databaseService.addLoses(2);
//        databaseService.addWins(2);
        //databaseService.changePassword(1, "s", "test");
//        User[] users = databaseService.getScoreboard();
//        for (int i=0; i<users.length; i++){
//            System.out.println(users[i]);
//        }
    }
}
