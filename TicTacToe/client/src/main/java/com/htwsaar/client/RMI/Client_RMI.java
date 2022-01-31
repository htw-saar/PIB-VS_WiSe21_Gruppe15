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


    private int testLoginData(String userName, String password){
        try {
            return clientStub.sendLoginData(userName, password);
        } catch(Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return 0;
        }
    }

    public int ShowScoreBoardAll(){
        try {
            List<String> stringList = new ArrayList<String>();
            stringList = clientStub.scoreboardRequest();
            for (int i = 0; i < stringList.size(); i++) {
                System.out.println(stringList.get(i) + "\n");
            }
            return 1;
        } catch(Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return 0;
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
