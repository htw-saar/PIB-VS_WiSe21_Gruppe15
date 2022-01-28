//Legacy Code for Database


//package com.htwsaar.server.legacyDatabase;
//
//import com.htwsaar.server.hibernate.entity.User;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.sql.*;
//import java.util.ArrayList;
//
//public class DatabaseService {
//    private static final Logger logger = LogManager.getLogger(DatabaseService.class);
//
//    // local MySqlDatenbank erstellen mit dem Namen gameServer
//    private final String DB_URL = "jdbc:mysql://localhost/gameserver";
//    private final String USER = "testuser";
//    private final String PASS = "test";
//
//    private final String SQL_GET_USERDATA = "SELECT Username, Wins, Loses, Score FROM user";
//    private Connection con;
//    private Statement stmt;
//
//    public DatabaseService() {
//        try {
//            logger.info("Starting initialization of the database");
//            initDatabase();
//        } catch (SQLException e) {
//            handleError(e);
//        }
//    }
//
//    private void initDatabase() throws SQLException {
//        con = DriverManager.getConnection(DB_URL, USER, PASS);
//        stmt = con.createStatement();
//        if (!tableExists("user")) {
//            createTable();
//        }
//    }
//
//    private boolean tableExists(String tableName) throws SQLException {
//        DatabaseMetaData meta = con.getMetaData();
//        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});
//        return resultSet.next();
//    }
//
//    private void executeUpdate(String command) {
//        try {
//            if (stmt != null) {
//                stmt.executeUpdate(command);
//            } else {
//                throw new SQLException();
//            }
//        } catch (SQLException e) {
//            handleError(e);
//        }
//    }
//
//    private ResultSet executeQuery(String command) {
//        try {
//            if (stmt != null) {
//                return stmt.executeQuery(command);
//            } else {
//                throw new SQLException("statement is null");
//            }
//        } catch (SQLException e) {
//            handleError(e);
//        }
//        return null;
//    }
//
//    private User getUserFromResultSet(ResultSet rs) {
//        User user = new User();
//        try {
//            if (rs != null){
//                user.setUsername(rs.getString("Username"));
//                user.setWins(rs.getInt("Wins"));
//                user.setLoses(rs.getInt("Loses"));
//                user.setScore(rs.getInt("Score"));
//            } else {
//                throw new SQLException();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return user;
//    }
//
//    private void handleError(Exception error) {
//        logger.error(error.getMessage());
//    }
//
//    /*
//     * -----------------------------------------------------------------------------
//     */
//
//    private void createTable() {
//        String command = "CREATE TABLE user(UserID int primary key auto_increment, Username varchar(255) UNIQUE ,Password varchar(255) NOT NULL, Wins int, Loses int, Score int)";
//        executeUpdate(command);
//    }
//
//    private User getUserData(String username) {
//        User user = new User();
//        try {
//            ResultSet rs = executeQuery(SQL_GET_USERDATA + " WHERE Username = " + username);
//            if (rs != null && rs.next()) {
//                user = getUserFromResultSet(rs);
//            }
//        } catch (SQLException e) {
//            handleError(e);
//        }
//        return user;
//    }
//
//    private void updateLosesAndWins(User user, int additionalWins, int additionalLoses) {
//        int score = user.getScore();
//        int wins = user.getWins() + additionalWins;
//        int loses = user.getLoses() + additionalLoses;
//        if (additionalWins < additionalLoses) {
//            score += 10;
//        } else {
//            if (score != 0) {
//                score -= 10;
//            }
//        }
//        String command = "UPDATE user SET Wins = " + wins + ", Loses = " + loses + ", Score = " + score + " WHERE Username = " + user.getUsername();
//        executeUpdate(command);
//        logger.info("Update wins or loses counter from user " + user.getUsername());
//    }
//
//    public void addUser(String username, String password) {
//        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
//            logger.error("Username or password can't be null or empty!");
//            throw new IllegalArgumentException();
//        }
//        String sql = "INSERT INTO user(Username, Password, Wins, Loses, Score) VALUES ('" + username + "', '"
//                + password + "', 0, 0, 0)";
//        executeUpdate(sql);
//        logger.info("Insert a new user in the database");
//    }
//
//    public void addWins(String username) {
//        User user = getUserData(username);
//        updateLosesAndWins(user, 1, 0);
//    }
//
//    public void addLoses(String username) {
//        User user = getUserData(username);
//        updateLosesAndWins(user, 0, 1);
//    }
//
//    public void changePassword(String username, String oldPassword, String newPassword) {
//        ResultSet rs = executeQuery(SQL_GET_USERDATA + " WHERE Username = " + username);
//        User user = getUserFromResultSet(rs);
//        if (oldPassword.equals(user.getPassword())) {
//            String sql = "UPDATE user SET Password = '" + newPassword + "' WHERE Username = " + username;
//            logger.info("Change password from a user");
//            executeUpdate(sql);
//        }
//    }
//
//    public User[] getScoreboard() {
//        ArrayList<User> users = new ArrayList<User>();
//        int counter = 0;
//        try {
//            ResultSet rs = executeQuery(SQL_GET_USERDATA + " ORDER BY Score DESC, Wins DESC");
//            logger.info("Get the scoreboard");
//            while (rs != null && rs.next()) {
//                User user = (getUserFromResultSet(rs));
//                users.add(user);
//                counter++;
//            }
//        } catch (SQLException e) {
//            handleError(e);
//        }
//        return users.toArray(new User[counter]);
//    }
//}
