package com.htwsaar.client;

import com.htwsaar.client.RMI.Client_RMI;
import com.htwsaar.client.UserInterface.Menu.Menu;

/**
 * Client programm
 *
 */
public class Client
{
    /**
     * Hauptfunktion der Klasse, startet automatisch beim Start und ruft die Startmethode auf
     *
     * @param args Übergebene Parameter bei Programmaufruf
     */
    public static void main( String[] args )
    {
        Client client = new Client();
        client.start(args);
    }

    /**
     * Initialisiert alle vom Clienten benoetigten Klassen und startet dann das Menue
     *
     * @param args Übergebene Parameter bei Programmaufruf
     */
    public void start(String[] args){
        Client_RMI client_rmi = new Client_RMI();
        Menu menu = new Menu(client_rmi);
        menu.startMenu();
    }
}
