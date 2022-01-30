package com.htwsaar.client.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client_RMI {
    public static ServerClient_Connect_Interface createStub(String host, int registryID) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, registryID);
            ServerClient_Connect_Interface stub = (ServerClient_Connect_Interface) registry.lookup("Server");
            return stub;
        } catch (Exception e) {
            System.err.println("Client Exception: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public static void start() {
        System.out.println("start");
    }

    public static void main(String[] args) {
        start();
    }
}