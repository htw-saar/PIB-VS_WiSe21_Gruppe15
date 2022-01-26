package com.htwsaar.server;

import com.htwsaar.server.Database.DatabaseService;
import com.htwsaar.server.Shared.User;

public class App {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        DatabaseService databaseService = new DatabaseService();
//        databaseService.addUser("Simon", "test");
//        databaseService.addUser("Alex", "test");

//         databaseService.addWins(1);
//         databaseService.addLoses(1);
//        databaseService.addLoses(2);
//        databaseService.addWins(2);
        //databaseService.changePassword(1, "s", "test");
        User[] users = databaseService.getScoreboard();
        for (int i=0; i<users.length; i++){
            System.out.println(users[i]);
        }
    }
}