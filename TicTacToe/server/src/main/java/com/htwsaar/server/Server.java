package com.htwsaar.server;
import com.htwsaar.server.RMI.Server_RMI;
import com.htwsaar.server.Services.DatabaseService;

public class Server {
    public static void main(String[] args) {
        Server server = new Server();
        server.start(args);
    }

  
    private void start(String[] args) {
        //Lokal oder Extern verwenden
//        System.setProperty("java.rmi.server.hostname", "simon.selfhost.co");
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        DatabaseService databaseService = new DatabaseService();
        Server_RMI server_rmi = new Server_RMI(databaseService);
        server_rmi.start_Server_RMI();
    }
}
