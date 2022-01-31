package com.htwsaar.client.RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Client_RMI {

    private Scanner input = new Scanner(System.in);
    private ServerClient_Connect_Interface clientStub;
    private String userName;
    private String password;

    //Konstanten für die einzelnen Funktionen
    private static final int SPIEL_ERSTELLEN = 1;
    private static final int SPIEL_BEITRETEN = 2;
    private static final int SCOREBOARD_ALL = 3;
    private static final int SCOREBOARD_SELF = 4;
    private static final int ENDE = 0;

    private int testLoginData(String userName, String password){
        try {
            return clientStub.sendLoginData(userName, password);
        } catch(Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return 0;
        }
    }

    private int ShowScoreBoardAll() throws RemoteException{
        try {
            List<String> stringList = new ArrayList<String>();
            stringList = clientStub.scoreboardRequest();
            for (int i = 0; i < stringList.size(); i++) {
                System.out.println(stringList.get(i) + "/n");
            }
            return 1;
        } catch(Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return 0;
        }
    }

    private int ShowOwnStats() throws  RemoteException{
        try {
            System.out.println(clientStub.scoreboardRequestForUser(userName));
            return 1;
        } catch(Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return 0;
        }
    }

    private ServerClient_Connect_Interface connectToServer() throws RemoteException {
        try
        {
            Registry registry = LocateRegistry.getRegistry("Server", 42424);
            ServerClient_Connect_Interface stub = (ServerClient_Connect_Interface) registry.lookup("Server");
            System.out.println("Server Verbindung besteht!/n");
            return stub;
        }
        catch (NotBoundException | RemoteException e){
            System.err.println("Client Exception: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public void client_menue() throws  RemoteException{
        Client_RMI client = new Client_RMI();
        clientStub = connectToServer();
        if(clientStub != null){
            System.out.println("Bitte Username eingeben!/n");
            userName = input.next();
            System.out.println("Bitte Passwort eingeben!/n");
            password = input.next();
            int ergebnis = testLoginData(userName, password);
            if (ergebnis == 1) {
                System.out.println("Login erfolgreich!/n");
                auswahlSchleife();
            } else {
                System.out.println("Login nicht erfolgreich!/n");
            }
        }
        else{
            System.err.println("Stub wurde nicht erstellt!/n");
        }
    }

    private void auswahlSchleife() {
        int funktion = -1;
        while (funktion != ENDE) {
            try {
                funktion = einlesenFunktion();
                ausfuehrenFunktion(funktion);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            } catch (InputMismatchException e) {
                System.out.println(e);
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Irgendeine Ausnahme gefangen: " + e);
                e.printStackTrace(System.out);
            }
        }
    }

    private int einlesenFunktion() {
        int funktion;
        System.out.print(   SPIEL_ERSTELLEN     + ": Spiel erstellen /n"    +
                            SPIEL_BEITRETEN     + ": Spiel beitreten /n"  +
                            SCOREBOARD_ALL      + ": Scoreboard ansehen /n"    +
                            SCOREBOARD_SELF     + ": eigene Statistik ansehen /n" +
                            ENDE                + ": Beenden -> ");

        funktion = input.nextInt();
        input.nextLine();
        return funktion;
    }

    private void ausfuehrenFunktion(int funktion) throws  RemoteException{
        int ergebnis;
        switch (funktion){
            case SPIEL_ERSTELLEN:
                //
                break;
            case SPIEL_BEITRETEN:
                //
                break;
            case SCOREBOARD_ALL:
                ergebnis = ShowScoreBoardAll();
                if(ergebnis == 0){
                    System.out.println("Scoreboard konnte nicht ausgegeben werden!");
                }
                break;
            case SCOREBOARD_SELF:
                ergebnis = ShowOwnStats();
                if(ergebnis == 0){
                    System.out.println("Scoreboard konnte nicht ausgegeben werden!");
                }
                break;
            case ENDE:
                System.out.println("Programmende");
                break;
            default:
                System.out.println("Falsche Funktion!");
        }
    }

    public void main(String[] args) throws RemoteException {
        client_menue();
    }
}
