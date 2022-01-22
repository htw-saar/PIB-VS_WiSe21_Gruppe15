package com.htwsaar.server;

import com.htwsaar.server.Database.DatabaseService;

public class App {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        DatabaseService databaseService = new DatabaseService();
        // databaseService.addLoses(1);
        //databaseService.changePassword(1, "s", "test");
    }
}