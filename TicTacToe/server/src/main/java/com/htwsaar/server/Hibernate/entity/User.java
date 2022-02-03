package com.htwsaar.server.Hibernate.entity;

import javax.persistence.*;

/**
 * User Entity Klasse, wird als Schema für die Hibernate Datenbank benutzt
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    private int wins;
    @Column
    private int loses;
    @Column
    private int score;


    public User() {
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public User(int userId, String username, String password) {
        setUserId(userId);
        setUsername(username);
        setPassword(password);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Eine ToString Methode um User formatiert auszugeben
     *
     * @return Gibt formatierten User als String aus
     */
    @Override
    public String toString() {
        return "username='" + username + '\'' +
                ", wins=" + wins +
                ", loses=" + loses +
                ", score=" + score;
    }
}
