package com.htwsaar.server.Services;

import com.htwsaar.server.Hibernate.dao.UserDao;
import com.htwsaar.server.Hibernate.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Eine Serviceklasse um Datenbank Operationen zu vereinfachen
 */
public class DatabaseService {
    private final Logger logger = LogManager.getLogger(DatabaseService.class);
    private UserDao userDao;

    /**
     * Konstruktor der die Datenbank initialisiert
     */
    public DatabaseService() {
        initDatabase();
    }

    /**
     * Eine Methode um die UserDao in eine Variable zu schreiben
     */
    private void initDatabase() {
        userDao = new UserDao();
    }

    /**
     * Eine Methode um einen User aus der Datenbank zu erhalten mit gegebenem Username
     * @param username Der Username des zu suchenden Users
     * @return User wenn ein User gefunden wurde, null wenn kein User gefunden wurde
     */
    public User getUserData(String username) {
        return userDao.getUser(username);
    }

    /**
     * Eine Methode um Loses und Wins zu ändern
     *
     * @param user Ein User, in dem Wins/Loses bearbeitet werden sollen
     * @param additionalWins Zahl der zu addierenden Wins
     * @param additionalLoses Zahl der zu addierenden Loses
     */
    private void updateLosesAndWins(User user, int additionalWins, int additionalLoses) {
        int score = user.getScore();
        if (additionalWins > additionalLoses) {
            int wins = user.getWins();
            user.setWins(wins + 1);
            user.setScore(score + 10);
        } else {
            int loses = user.getLoses();
            user.setLoses(loses + 1);
            if (score >= 10) {
                user.setScore(score - 10);
            }
        }
        userDao.updateUser(user);
    }

    /**
     * Eine Methode um einen neuen Nutzer anzulegen
     *
     * @param username Der Username eines neuen Nutzers
     * @param password Das Passwort eines neuen Nutzers
     */
    public void addUser(String username, String password) {
        if (username != null && !username.isEmpty()) {
            if (userDao.getUser(username) == null) {
                if (password != null && !password.isEmpty()) {
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

    /**
     * Eine Methode um einem Nutzer einen Wins zu addieren
     *
     * @param username Der Username, dem ein Win addiert werden soll
     */
    public void addWin(String username) {
        User user = getUserData(username);
        updateLosesAndWins(user, 1, 0);
    }

    /**
     * Eine Methode um einem Nutzer einen Loose zu addieren
     *
     * @param username Der Username, dem ein Loose addiert werden soll
     */
    public void addLose(String username) {
        User user = getUserData(username);
        updateLosesAndWins(user, 0, 1);
    }

    /**
     * Eine Methode um das Passwort eines Benutzers zu ändern
     *
     * @param username Der Username des zu bearbeitenden Users
     * @param oldPassword Das alte Passwort
     * @param newPassword Das neue Passwort
     */
    public void changePassword(String username, String oldPassword, String newPassword) {
        if (newPassword != null && !newPassword.isEmpty()) {
            if (oldPassword != null && !oldPassword.isEmpty()) {
                User user = getUserData(username);
                if (oldPassword.equals(user.getPassword())) {
                    user.setPassword(newPassword);
                    userDao.updateUser(user);
                }
            }
        }

    }

    /**
     * Eine Methode um alle Benutzer auszugeben
     *
     * @return Gibt alle Benutzer aus
     */
    public List<User> getScoreboard() {
        return userDao.getScoreboard();
    }
}
