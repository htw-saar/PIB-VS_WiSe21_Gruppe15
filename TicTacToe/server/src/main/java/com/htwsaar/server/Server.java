package com.htwsaar.server;

import com.htwsaar.server.Hibernate.entity.User;
import com.htwsaar.server.RMI.Server_RMI;
import com.htwsaar.server.Services.DatabaseService;

public class Server {
    public static void main(String[] args) {
        Server server = new Server();
        server.start(args);
    }

  
    private void start(String[] args) {
        DatabaseService databaseService = new DatabaseService();
        //databaseService.addUser("oliver","test");
        Server_RMI server_rmi = new Server_RMI();
        server_rmi.start_Server_RMI();
    }
}
