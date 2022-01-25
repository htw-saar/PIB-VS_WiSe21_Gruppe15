package com.htwsaar.server.Shared;

public class User {
    private int userId;
    private String username;
    private String password;
    private int wins;
    private int loses;
    private int score;

    public User(String username, int wins, int loses, int score) {
        setUsername(username);
        setWins(wins);
        setLoses(loses);
        setScore(score);
    }
    public User(String username, String password, int wins, int loses, int score) {
        setUsername(username);
        setPassword(password);
        setWins(wins);
        setLoses(loses);
        setScore(score);
    }

    public User(String username, String password) {
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

    @Override
    public String toString() {
        return  "username='" + username + '\'' +
                ", wins=" + wins +
                ", loses=" + loses +
                ", score=" + score;
    }
}
