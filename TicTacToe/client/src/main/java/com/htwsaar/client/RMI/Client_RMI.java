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

public class Client_RMI {
    private static final Logger logger = LogManager.getLogger(Client_RMI.class);
    private ServerClient_Connect_Interface clientStub;
    private String loggedInUser;

    public String getLoggedInUser() {
        try {
            return loggedInUser;
        } catch (Exception e) {
            logger.error("Client exception: " + e.toString());
            return "logged in user not found";
        }
    }


    public int getUserId(String username) {
        try {
            return clientStub.getUserId(username);
        } catch (Exception e) {
            logger.error("Client exception: " + e.toString());
            return -1;
        }
    }

    public Boolean createGame(String username) {
        try {
            return clientStub.createGame(username);
        } catch (Exception e) {
            logger.error("Client exception: " + e.toString());
            return false;
        }
    }

    public String[] returnGameboard(String username) {
        try {
            int userId;
            userId = clientStub.getUserId(username);
            return clientStub.returnGameboard(username);
        } catch (Exception e) {
            logger.error("Client exception: " + e.toString());
            return null;
        }
    }

    public Boolean joinGame(int joinCode, String username) {
        try {
            return clientStub.joinGame(username, joinCode);
        } catch (Exception e) {
            logger.error("Client exception: " + e.toString());
            return false;
        }
    }

    public TicTacToe.Winner setField(String username, int pos) {
        try {
            return clientStub.setField(username, pos);
        } catch (Exception e) {
            logger.error("Client exception: " + e.toString());
            return TicTacToe.Winner.NONE;
        }
    }

    private boolean testLoginData(String userName, String password) {
        try {
            return clientStub.sendLoginData(userName, password);
        } catch (Exception e) {
            logger.error("Client exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    public void ShowScoreBoardAll() {
        try {
            String format = " %2s %12s %2s %6s %2s %6s %2s %6s %2s";
            String spacer = "  +---------------+---------+---------+---------+";
            System.out.println(spacer);
            System.out.println(String.format(format, "|", "Username", "|", "Wins", "|", "Loses", "|", "Score", "|"));
            System.out.println(spacer);
//            System.out.println(String.format(format,"-"));
            List<String> stringList;
            stringList = clientStub.scoreboardRequest();
            if (stringList != null) {
                for (String score : stringList) {
                    System.out.println(score);
                }
            }
            System.out.println(spacer);
        } catch (Exception e) {
            logger.error("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public int ShowOwnStats(String username) {
        try {
            System.out.println(clientStub.scoreboardRequestForUser(username));
            return 1;
        } catch (Exception e) {
            logger.error("Client exception: " + e.toString());
            e.printStackTrace();
            return 0;
        }
    }

    private ServerClient_Connect_Interface connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 42424);
            ServerClient_Connect_Interface stub = (ServerClient_Connect_Interface) registry.lookup("Hello");
            System.out.println("Server Verbindung besteht!\n");
            return stub;
        } catch (NotBoundException | RemoteException e) {
            logger.error("Client exception: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public Boolean login(String username, String password) {
        clientStub = connectToServer();
        if (clientStub != null) {
            loggedInUser = username;
            return testLoginData(username, password);
        } else {
            logger.error("Stub wurde nicht erstellt!\n");
        }
        return false;
    }

    public Boolean checkGameStart(String username) {
        try {
            return clientStub.checkGameStart(username);
        } catch (RemoteException e) {
            logger.error("Client exception: " + e.toString());
            e.printStackTrace();
            return null;
        }

    }

    public String getActivePlayer(String username){
        try {
            return clientStub.getActivePlayer(username);
        } catch (RemoteException e){
            logger.error("Client exception: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }
}
