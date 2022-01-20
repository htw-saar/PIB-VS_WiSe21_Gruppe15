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
        String sql = "CREATE TABLE user(UserID int primary key auto_increment, Username varchar(255) UNIQUE ,Password varchar(255) NOT NULL, Wins int, Loses int)";
        stmt.executeUpdate(sql);
    }

    public void addUser(String username, String password) throws SQLException {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String sql = "INSERT INTO user(Username, Password, Wins, Loses) VALUES ('" + username + "', '" + password + "', 0, 0)";
        stmt.executeUpdate(sql);
    }

    public void addWins(int UserID) {

    }

    public void addLoses(int UserID) {

    }

    public void changePassword(int UserID, String password) {

    }

    public void getScoreboard(int UserID) {

    }
}
