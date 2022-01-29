package com.htwsaar.server.RMI;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server_RMI implements ServerClient_Connect_Interface{

    @Override
    public int sendLoginData(String name, String password) throws RemoteException {
        return 0;
    }

    @Override
    public int ScoreboardRequest() throws RemoteException{
        return 0;
    }

    @Override
    public int ScoreboardRequestForUser(String name) throws RemoteException{
        return 0;
    }

    @Override
    public int createGame() throws  RemoteException{
        return 0;
    }

    @Override
    public int joinGame(int joinCode) throws RemoteException{
        return 0;
    }

    @Override
    public int setX(int x, int y) throws RemoteException{
        return 0;
    }

    @Override
    public int setO(int x, int y) throws RemoteException{
        return 0;
    }
}
