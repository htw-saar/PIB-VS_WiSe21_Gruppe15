package com.htwsaar.client.RMI;

import com.htwsaar.server.RMI.ServerClient_Connect_Interface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Client_RMI {
    private ServerClient_Connect_Interface clientStub;

    public int createGame() throws RemoteException {
        try {
            return clientStub.createGame();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            return 0;
        }
    }

    public int joinGame(int joinCode) throws RemoteException {
        try {
            return clientStub.joinGame(joinCode);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            return 0;
        }
    }

    public int setField(String username, int pos) throws RemoteException {
        try {
            return clientStub.setField(username, pos);
        } catch(Exeption e) {
            System.err.println("Client exception: " + e.toString());
            return 0;
        }
    }

    private int testLoginData(String userName, String password){
        try {
            return clientStub.sendLoginData(userName, password);
        } catch(Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return 0;
        }
    }

    public void ShowScoreBoardAll(){
        try {
            String format = " %2s %12s %2s %6s %2s %6s %2s %6s %2s";
            String spacer = "  +---------------+---------+---------+---------+";
            System.out.println(spacer);
            System.out.println(String.format(format, "|", "Username", "|", "Wins", "|", "Loses", "|", "Score" , "|"));
            System.out.println(spacer);
//            System.out.println(String.format(format,"-"));
            List<String> stringList;
            stringList = clientStub.scoreboardRequest();
            if (stringList != null){
                for (String score : stringList) {
                    System.out.println(score);
                }
            }
            System.out.println(spacer);
        } catch(Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public int ShowOwnStats(String username){
        try {
            System.out.println(clientStub.scoreboardRequestForUser(username));
            return 1;
        } catch(Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return 0;
        }
    }

    private ServerClient_Connect_Interface connectToServer(){
        try
        {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 42424);
            ServerClient_Connect_Interface stub = (ServerClient_Connect_Interface) registry.lookup("Hello");
            System.out.println("Server Verbindung besteht!\n");
            return stub;
        }
        catch (NotBoundException | RemoteException e){
            System.err.println("Client Exception: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public Boolean login(String username, String password){
        clientStub = connectToServer();
        if(clientStub != null){
            int ergebnis = testLoginData(username, password);
            if (ergebnis == 1) {
                return true;
            } else {
                return false;
            }
        }
        else{
            System.err.println("Stub wurde nicht erstellt!\n");
        }
        return false;
    }



}
