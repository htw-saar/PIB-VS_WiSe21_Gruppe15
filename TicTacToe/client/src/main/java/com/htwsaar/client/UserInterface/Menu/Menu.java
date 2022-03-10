package com.htwsaar.client.UserInterface.Menu;

import com.htwsaar.client.UserInterface.TicTacToe.GameLogic;
import com.htwsaar.client.RMI.Client_RMI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import java.util.Scanner;

public class Menu {
    private static final Logger logger = LogManager.getLogger(Menu.class);

    private final int ENDE = 0;
    private final int LOGIN = 1;
    private final int SIGNUP = 2;
    private boolean isAuthenticated = false;

    private final int SPIEL_ERSTELLEN = 1;
    private final int SPIEL_BEITRETEN = 2;
    private final int BESTENLISTE = 3;
    private final int LOGOUT = 9;
    private int funktion = -1;
    private final Client_RMI client_rmi;
    private final Scanner input = new Scanner(System.in);
    private String username;

    public Menu(Client_RMI client_rmi) {
        this.client_rmi = client_rmi;
    }

    public void startMenu() {
        while (funktion != ENDE) {
            try {
                funktion = einlesenFunktion();
                ausfuehrenFunktion(funktion);
            } catch (Exception e) {
                logger.error(e);
                input.nextLine();
            }
        }
    }

    private int einlesenFunktion() {
        String format = " %2s %6s %2s %22s %2s";
        String spacer = "  +---------+-------------------------+";
        System.out.println(spacer);
        System.out.printf((format) + "%n", "|", "Nummer", "|", "Funktion", "|");
        System.out.println(spacer);
        if (isAuthenticated){
            printInGame(format);
        } else {
            printLogin(format);
        }

        System.out.println(spacer);

        return intEinlesen();
    }

    private void printInGame(String format){
        System.out.printf((format) + "%n", "|", SPIEL_ERSTELLEN, "|", "Spiel erstellen", "|");
        System.out.printf((format) + "%n", "|", SPIEL_BEITRETEN, "|", "Spiel beitreten", "|");
        System.out.printf((format) + "%n", "|", BESTENLISTE, "|", "Bestenliste anzeigen", "|");
        System.out.printf((format) + "%n", "|", LOGOUT, "|", "Ausloggen", "|");
    }

    private void printLogin(String format){
        System.out.printf((format) + "%n", "|", LOGIN, "|", "Einloggen", "|");
        System.out.printf((format) + "%n", "|", SIGNUP, "|", "Registrieren", "|");
        System.out.printf((format) + "%n", "|", ENDE, "|", "Beenden", "|");
    }

    private void ausfuehrenFunktion(int funktion) throws RemoteException {
        if (isAuthenticated){
            gameFunctions(funktion);
        } else {
            loginFunctions(funktion);
        }
        System.out.println("\n\n\n\n");
    }

    private void gameFunctions(int funktion) throws RemoteException {
        switch (funktion) {
            case SPIEL_ERSTELLEN:
                System.out.println("Spiel erstellen:");

                GameLogic.startGame(client_rmi, username);
                //client_rmi.returnGameboard();
                break;
            case SPIEL_BEITRETEN:
                System.out.println("Spiel beitreten:");
                GameLogic.joinGame(3, client_rmi, username); //leer
                break;
            case BESTENLISTE:
                System.out.println("Bestenliste:");
                client_rmi.ShowScoreBoardAll();
                break;
            case LOGOUT:
                logout();
                System.out.println("Benutzer wird abgemeldet.");
                break;
            default:
                System.out.println("Fehlerhafte Auswahl einer Funktion!");
                break;
        }
    }

    private void loginFunctions(int funktion){
        switch (funktion){
            case LOGIN:
                // TODO LOGIN Missing logic
                login();
                break;
            case SIGNUP:
                // TODO SIGNUP Missing logic
                signup();
                break;
            case ENDE:
                // TODO Ende Programm
                break;
            default:
                logger.error("Fehlerhafte Auswahl einer Funktion!");
                break;
        }
    }

    //Alpha methode (User kann noch nicht angelegt werden)
    private void login() {
        username = null;
        input.nextLine();
        int versuche = 0;
        String pw;
        Boolean log = false;
        System.out.println("Benutzername: ");
        username = input.nextLine();
        System.out.println("Passwort: ");
        pw = input.nextLine();
        log = client_rmi.login(username, pw);
        if(log && versuche <= 3) {
            System.out.println("Login war erfolgreich!");
        } else if(!log && versuche <= 3) {
            logger.error("Login fehlgeschlagen!\nVersuchen Sie es erneut.");
            versuche++;
            login();
        } else {
            logger.error("Login fehlgeschlagen!\nLogin wurde gesperrt!");
            //massnahme ergreifen
        }
        setAuthenticated(true);
    }

    private void signup(){
        // TODO REPLACE SIGNUP
        client_rmi.login("simon", "test");
        setAuthenticated(true);
    }

    private void logout(){
        setAuthenticated(false);
    }

    /**
     * Eine Methode um Int-Werte einzulesen
     *
     * @return int-wert(um damit in der Fachklasse zu rechnen)
     */
    private int intEinlesen() {
        System.out.println("\nAuswahl eingeben: ");
        return input.nextInt();
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
