package com.htwsaar.server.Database;

import java.sql.*;

public class DatabaseService {
    // local MySqlDatenbank erstellen mit dem Namen gameServer
    private final String DB_URL = "jdbc:mysql://localhost/gameServer";
    private final String USER = "root";
    private final String PASS = "test";
    private Connection con;
    private Statement stmt;

    public DatabaseService() {
        try {
            initDatabase();
        } catch (SQLException e) {
            handleError(e);
        }
    }

    private void initDatabase() throws SQLException {
        con = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = con.createStatement();
        if (!tableExists("user")) {
            createTable();
        }
    }

    private boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData meta = con.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] { "TABLE" });

        return resultSet.next();
    }

    private void executeUpdate(String command) {
        try {
            stmt.executeUpdate(command);
        } catch (SQLException e) {
            handleError(e);
        }

    }

    private void handleError(Exception error) {
        // TODO: handle exception
        System.out.println(error);
    }

    /*
     * -----------------------------------------------------------------------------
     * -------------
     */

    private void createTable() {
        String command = "CREATE TABLE user(UserID int primary key auto_increment, Username varchar(255) UNIQUE ,Password varchar(255) NOT NULL, Wins int, Loses int, Games int)";
        executeUpdate(command);
    }

    public void addUser(String username, String password) {
        try {
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                throw new IllegalArgumentException();
            }
            String sql = "INSERT INTO user(Username, Password, Wins, Loses, Games) VALUES ('" + username + "', '"
                    + password + "', 0, 0, 0)";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            handleError(e);
        }

    }

    public void addWins(int userID) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT Wins, Games FROM user WHERE UserID = " + userID);
            if (rs.next()) {
                int wins = rs.getInt("Wins") + 1;
                int games = rs.getInt("Games") + 1;

                String sql = "UPDATE user SET Wins = " + wins + ", Games = " + games + " WHERE UserID = " + userID;
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            handleError(e);
        }
    }

    public void addLoses(int userID) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT Loses, Games FROM user WHERE UserID = " + userID);
            if (rs.next()) {
                int loses = rs.getInt("Loses") + 1;
                int games = rs.getInt("Games") + 1;

                String sql = "UPDATE user SET Loses = " + loses + ", Games = " + games + " WHERE UserID = " + userID;
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            handleError(e);
        }
    }

    public void changePassword(int userID, String oldPassword, String newPassword) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT Password FROM user WHERE UserID = " + userID);
            if (rs.next()) {
                String pw = rs.getString("Password");

                if (oldPassword.equals(pw)) {
                    String sql = "UPDATE user SET Password = '" + newPassword + "' WHERE UserID = " + userID;
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            handleError(e);
        }
    }

    public void getScoreboard() {
        try {
            ResultSet rs = stmt.executeQuery("SELECT Username, Wins, Loses FROM user ORDER BY Games, Wins DESC");
            while (rs.next()) {
                String username = rs.getString("Username");
                int wins = rs.getInt("Wins");
                int loses = rs.getInt("Loses");

                System.out.println(username + ": " + wins + " - " + loses);
            }
            // TODO return users as array of users
        } catch (SQLException e) {
            handleError(e);
        }
    }
}
