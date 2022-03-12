package com.htwsaar.client.RMI;

import com.htwsaar.server.Game.TicTacToe;
import com.htwsaar.server.RMI.ServerClient_Connect_Interface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Client_RMI {
    private static final Logger logger = LogManager.getLogger(Client_RMI.class);
    private static final String HOST_IP = "127.0.0.1";
    private static final int PORT = 42424;
    private static final String REGISTRY = "GAME";

    private ServerClient_Connect_Interface clientStub;
    private String loggedInUser;

    public Client_RMI(){
        init_RMI();
        logger.debug("Verbindung zum Server wurde erfolgreich hergestellt!");
    }

    private void init_RMI(){
        while(clientStub == null){
            clientStub = connectToServer();
            if (clientStub == null){
                System.out.println("Verbindung zum Server fehlgeschlagen!");
                System.out.println("Nächster Versuch startet gleich...");
                waitOn(2);
            }
        }
    }

    public Boolean userLoginExists(String name) {
        try {
            return clientStub.userLoginExists(name);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return false;
        }
    }

    public Boolean createLoginData(String name, String password) {
        try {
            return clientStub.createLoginData(name, password);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return false;
        }
    }

    public String getLoggedInUser() {
        try {
            return loggedInUser;
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return "logged in user not found";
        }
    }


    public int getUserId(String username) {
        try {
            return clientStub.getUserId(username);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return -1;
        }
    }

    public void createGame(String username) {
        try {
            clientStub.createGame(username);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
        }
    }

    public String[] returnGameboard(String username) {
        try {
            return clientStub.returnGameboard(username);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return null;
        }
    }

    public Boolean joinGame(int joinCode, String username) {
        try {
            return clientStub.joinGame(username, joinCode);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return false;
        }
    }

    public TicTacToe.Winner setField(String username, int pos) {
        try {
            return clientStub.setField(username, pos);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return TicTacToe.Winner.NONE;
        }
    }

    private boolean testLoginData(String userName, String password) {
        try {
            return clientStub.sendLoginData(userName, password);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return false;
        }
    }

    public void ShowScoreBoardAll() {
        try {
            String format = " %2s %12s %2s %6s %2s %6s %2s %6s %2s";
            String spacer = "  +---------------+---------+---------+---------+";
            System.out.println(spacer);
            System.out.printf((format) + "%n", "|", "Username", "|", "Wins", "|", "Loses", "|", "Score", "|");
            System.out.println(spacer);
            List<String> stringList;
            stringList = clientStub.scoreboardRequest();
            if (stringList != null) {
                for (String score : stringList) {
                    System.out.println(score);
                }
            }
            System.out.println(spacer);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
        }
    }

    public int ShowOwnStats(String username) {
        try {
            System.out.println(clientStub.scoreboardRequestForUser(username));
            return 1;
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return 0;
        }
    }

    private ServerClient_Connect_Interface connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry(HOST_IP, PORT);
            ServerClient_Connect_Interface stub = (ServerClient_Connect_Interface) registry.lookup(REGISTRY);
            System.out.println("Server Verbindung besteht!\n");
            return stub;
        } catch (NotBoundException | RemoteException e) {
            logger.error("Client exception: " + e);
            return null;
        }
    }

    public Boolean login(String username, String password) {
        loggedInUser = username;
        return testLoginData(username, password);
    }

    public Boolean checkGameStart(String username) {
        try {
            return clientStub.checkGameStart(username);
        } catch (RemoteException e) {
            logger.error("Client exception: " + e);
            return null;
        }

    }

    public String getActivePlayer(String username) {
        try {
            return clientStub.getActivePlayer(username);
        } catch (RemoteException e) {
            logger.error("Client exception: " + e);
            return null;
        }
    }

    public TicTacToe.Winner getGameStatus(String username) {
        try{
            return clientStub.getGameStatus(username);
        } catch (RemoteException e) {
            logger.error("Client exception: " + e);
            return null;
        }
    }

    private void waitOn(int sec){
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
