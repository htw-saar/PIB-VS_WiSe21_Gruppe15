package com.htwsaar.server.RMI;
import com.htwsaar.server.Hibernate.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerClient_Connect_Interface extends Remote {

        int sendLoginData(String name, String password) throws RemoteException;

        List<String> scoreboardRequest() throws RemoteException;

        String scoreboardRequestForUser(String name) throws RemoteException;

        int createGame() throws  RemoteException;

        int joinGame(int joinCode) throws RemoteException;

        int setX(int x, int y) throws RemoteException;

        int setO(int x, int y) throws RemoteException;
    }
