package com.htwsaar.server;
import java.rmi.Remote;
import java.rmi.RemoteException;

    public interface ServerClient_Connect_Interface extends Remote {

        int sendLoginData(String name, String password) throws RemoteException;

        int ScoreboardRequest() throws RemoteException;

        int ScoreboardRequestForUser(String name) throws RemoteException;

        int createGame() throws  RemoteException;

        int joinGame(int joinCode) throws RemoteException;

        int setX(int x, int y) throws RemoteException;

        int setO(int x, int y) throws RemoteException;
    }
