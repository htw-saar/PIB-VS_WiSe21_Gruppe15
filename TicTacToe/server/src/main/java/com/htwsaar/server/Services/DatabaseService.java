package com.htwsaar.server.Services;

import com.htwsaar.server.Hibernate.dao.UserDao;
import com.htwsaar.server.Hibernate.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DatabaseService {
    private final Logger logger = LogManager.getLogger(DatabaseService.class);
    private UserDao userDao;

    public DatabaseService() {
        initDatabase();
    }

    private void initDatabase() {
        userDao = new UserDao();
    }

    /*
     * -----------------------------------------------------------------------------
     */

    public User getUserData(String username) {
        return userDao.getUser(username);
    }

    private void updateLosesAndWins(User user, int additionalWins, int additionalLoses) {
        int score = user.getScore();
        if (additionalWins > additionalLoses){
            int wins = user.getWins();
            user.setWins(wins + 1);
            user.setScore(score + 10);
        } else {
            int loses = user.getLoses();
            user.setLoses(loses + 1);
            if (score >= 10 ){
                user.setScore(score - 10);
            }
        }
        userDao.updateUser(user);
    }

    public void addUser(String username, String password) {
        if (username != null && !username.isEmpty()){
            if (userDao.getUser(username) == null){
                if (password != null && !password.isEmpty()){
                    User user = new User(username, password);
                    userDao.saveUser(user);
                } else {
                    logger.error("Password is empty!");
                }
            } else {
                logger.error("Username is already taken!");
            }
        } else {
            logger.error("Username is empty!");
        }

    }

    public void addWin(String username) {
        User user = getUserData(username);
        updateLosesAndWins(user, 1, 0);
    }

    public void addLose(String username) {
        User user = getUserData(username);
        updateLosesAndWins(user, 0, 1);
    }

    public void changePassword(String username, String oldPassword, String newPassword) {
        if (newPassword != null && !newPassword.isEmpty()){
            if (oldPassword != null && !oldPassword.isEmpty()){
                User user = getUserData(username);
                if (oldPassword.equals(user.getPassword())){
                    user.setPassword(newPassword);
                    userDao.updateUser(user);
                }
            }
        }

    }

    public List<User> getScoreboard() {
        return userDao.getScoreboard();
    }
}
