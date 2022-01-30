package main.java.com.htwsaar.client.RMI;

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

    public void testLoginData(String userName, String password, ServerClient_Connect_Interface stub){
        try {
            int ergebnis = stub.sendLoginData(userName, password);
            if (ergebnis == 1) {
                System.out.println("Login erfolgreich!");
            } else {
                System.out.println("Login nicht erfolgreich!");
            }
        } catch(Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        //
    }
}
