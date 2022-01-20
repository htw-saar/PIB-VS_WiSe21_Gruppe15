package com.htwsaar.server.Database;

import java.sql.*;

public class DatabaseService {
    // local MySqlDatenbank erstellen mit dem Namen gameServer
    private final String DB_URL = "jdbc:mysql://localhost/gameServer";
    private final String USER = "root";
    private final String PASS = "test";
    private final Connection con;
    private final Statement stmt;

    public DatabaseService() throws SQLException {
        con = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = con.createStatement();
    }

    public void run() throws SQLException {
        if (!tableExists("user")) {
            createTable();
        }
    }

    private boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData meta = con.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});

        return resultSet.next();
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE user(UserID int primary key auto_increment, Username varchar(255) UNIQUE ,Password varchar(255) NOT NULL, Wins int, Loses int, Games int)";
        stmt.executeUpdate(sql);
    }

    public void addUser(String username, String password) throws SQLException {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String sql = "INSERT INTO user(Username, Password, Wins, Loses, Games) VALUES ('" + username + "', '" + password + "', 0, 0, 0)";
        stmt.executeUpdate(sql);
    }

    public void addWins(int UserID) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT Wins, Games FROM user WHERE UserID = " + UserID);
        if (rs.next()) {
            int wins = rs.getInt("Wins") + 1;
            int games = rs.getInt("Games") + 1;

            String sql = "UPDATE user SET Wins = " + wins + ", Games = " + games + " WHERE UserID = " + UserID;
            stmt.executeUpdate(sql);
        }
    }

    public void addLoses(int UserID) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT Loses, Games FROM user WHERE UserID = " + UserID);
        if (rs.next()) {
            int loses = rs.getInt("Loses") + 1;
            int games = rs.getInt("Games") + 1;

            String sql = "UPDATE user SET Loses = " + loses + ", Games = " + games + " WHERE UserID = " + UserID;
            stmt.executeUpdate(sql);
        }
    }

    public void changePassword(int UserID, String oldPassword, String NewPassword) {

    }

    public void getScoreboard() throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT Username, Wins, Loses FROM user ORDER BY Wins DESC");
        while (rs.next()) {
            String username = rs.getString("Username");
            int wins = rs.getInt("Wins");
            int loses = rs.getInt("Loses");

            System.out.println(username + ": " + wins + " - " + loses);
        }
    }
}
