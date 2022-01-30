package main.java.com.htwsaar.client.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class verbindung_client {
    public static Interface_Verbindung createStub(String host, int registryID) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, registryID);
            Interface_Verbindung stub = (Interface_Verbindung) registry.lookup("Server");
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
