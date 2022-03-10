package com.htwsaar.server.RMI;

import com.htwsaar.server.Game.TicTacToe;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerClient_Connect_Interface extends Remote {

        Boolean userLoginExists(String name) throws RemoteException;

        Boolean createLoginData(String name, String password) throws RemoteException;

        Boolean sendLoginData(String name, String password) throws RemoteException;

        List<String> scoreboardRequest() throws RemoteException;

        String scoreboardRequestForUser(String name) throws RemoteException;

        Boolean createGame(String username) throws  RemoteException;

        Boolean joinGame(String username, int joinCode) throws RemoteException;

        TicTacToe.Winner setField(String username, int pos) throws RemoteException;

        String getActivePlayer(String username) throws RemoteException;

        String[] returnGameboard(String username) throws RemoteException;

        Boolean checkGameStart(String username) throws RemoteException;

        TicTacToe.Winner getGameStatus(String username) throws RemoteException;

        int getUserId(String username) throws RemoteException;
    }
