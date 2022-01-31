package com.htwsaar.client;

import com.htwsaar.client.RMI.Client_RMI;
import com.htwsaar.client.UserInterface.Menu.Menu;

/**
 * Client programm
 *
 */
public class Client
{
    public static void main( String[] args )
    {
        Client client = new Client();
        client.start(args);
    }

    public void start(String[] args){
        Client_RMI client_rmi = new Client_RMI();
        client_rmi.login("Simon", "test");
        Menu menu = new Menu(client_rmi);
        menu.startMenu();
    }
    //test
}
