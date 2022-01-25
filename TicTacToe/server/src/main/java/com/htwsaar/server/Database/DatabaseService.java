package com.htwsaar.server.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;

public class DatabaseService {
    private static final Logger logger = LogManager.getLogger(DatabaseService.class);

    // local MySqlDatenbank erstellen mit dem Namen gameServer
    private final String DB_URL = "jdbc:mysql://localhost/gameserver";
    private final String USER = "testuser";
    private final String PASS = "test";
    private Connection con;
    private Statement stmt;

    public DatabaseService() {
        try {
            logger.info("Starting initialization of the database");
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

    private ResultSet executeQuery(String command) {
        try {
            return stmt.executeQuery(command);
        } catch (SQLException e) {
            handleError(e);
        }
        return null;
    }

    private void handleError(Exception error) {
        logger.error(error.getMessage());
    }

    /*
     * -----------------------------------------------------------------------------
     * -------------
     */

    private void createTable() {
        String command = "CREATE TABLE user(UserID int primary key auto_increment, Username varchar(255) UNIQUE ,Password varchar(255) NOT NULL, Wins int, Loses int, Score int)";
        executeUpdate(command);
    }

    public void addUser(String username, String password) {
        try {
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                logger.error("Username or password can't be null or empty!");
                throw new IllegalArgumentException();
            }
            String sql = "INSERT INTO user(Username, Password, Wins, Loses, Score) VALUES ('" + username + "', '"
                    + password + "', 0, 0, 0)";
            logger.info("Insert a new user in the database");
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            handleError(e);
        }

    }

    public void addWins(int userID) {
        try {
            ResultSet rs = executeQuery("SELECT Wins, Score FROM user WHERE UserID = " + userID);
            if (rs.next()) {
                int wins = rs.getInt("Wins") + 1;
                int score = rs.getInt("Score") + 10;

                String sql = "UPDATE user SET Wins = " + wins + ", Score = " + score + " WHERE UserID = " + userID;
                logger.info("Update wins counter from a user");
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            handleError(e);
        }
    }

    public void addLoses(int userID) {
        try {
            ResultSet rs = executeQuery("SELECT Loses, Score FROM user WHERE UserID = " + userID);
            if (rs.next()) {
                int loses = rs.getInt("Loses") + 1;

                int score = rs.getInt("Score");
                if (score != 0){
                    score -= 10;
                }

                String sql = "UPDATE user SET Loses = " + loses + ", Score = " + score + " WHERE UserID = " + userID;
                logger.info("Update loses counter from a user");
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            handleError(e);
        }
    }

    public void changePassword(int userID, String oldPassword, String newPassword) {
        try {
            ResultSet rs = executeQuery("SELECT Password FROM user WHERE UserID = " + userID);
            if (rs.next()) {
                String pw = rs.getString("Password");

                if (oldPassword.equals(pw)) {
                    String sql = "UPDATE user SET Password = '" + newPassword + "' WHERE UserID = " + userID;
                    logger.info("Change password from a user");
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            handleError(e);
        }
    }

    public void getScoreboard() {
        try {
            logger.info("Get the scoreboard");
            ResultSet rs = executeQuery("SELECT Username, Wins, Loses, Score FROM user ORDER BY Score DESC, Wins DESC");
            while (rs.next()) {
                String username = rs.getString("Username");
                int wins = rs.getInt("Wins");
                int loses = rs.getInt("Loses");
                int score = rs.getInt("Score");
                System.out.println(username + ": " + score + " -> " + wins + " - " + loses);
            }
            // TODO return users as array of users
        } catch (SQLException e) {
            handleError(e);
        }
    }
}
