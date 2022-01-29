package com.htwsaar.server;

import com.htwsaar.server.Hibernate.dao.UserDao;
import com.htwsaar.server.Hibernate.entity.User;
import com.htwsaar.server.Services.DatabaseService;

public class App {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        DatabaseService databaseService = new DatabaseService();
//        databaseService.addUser("Simon", "test");
        System.out.println(databaseService.getScoreboard());
    }
}
