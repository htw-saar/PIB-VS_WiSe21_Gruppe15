package com.htwsaar.server;

import com.htwsaar.server.Database.DatabaseService;

import java.sql.SQLException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        try {
            DatabaseService databaseService = new DatabaseService();
            databaseService.run();
        } catch (
                SQLException ex) {
            System.out.println("FEHLER (SQLException): " + ex.getMessage());
        }
    }
}
