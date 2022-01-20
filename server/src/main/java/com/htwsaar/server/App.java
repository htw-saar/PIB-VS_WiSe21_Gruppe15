package com.htwsaar.server;

import com.htwsaar.server.Database.DatabaseService;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        try {
            DatabaseService databaseService = new DatabaseService();
            databaseService.run();
            databaseService.addUser("Simon", "test");
        } catch (SQLException ex) {
            System.out.println("FEHLER (SQLException): " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("FEHLER (IllegalArgumentException): " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("FEHLER: " + ex.getMessage());
        }
    }
}
