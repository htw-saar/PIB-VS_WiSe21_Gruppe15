package com.htwsaar.server;

import com.htwsaar.server.RMI.Server_RMI;

public class App {
    public static void main(String[] args) {
        App server = new App();
        server.start(args);
    }

    private void start(String[] args) {
        Server_RMI server_rmi = new Server_RMI();
        server_rmi.start_Server_RMI();
    }
}
