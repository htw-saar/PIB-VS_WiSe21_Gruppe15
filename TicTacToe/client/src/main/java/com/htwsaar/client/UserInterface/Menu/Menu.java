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
    private GameLogic gameLogic;

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

    private void gameFunctions(int funktion) {
        switch (funktion) {
            case SPIEL_ERSTELLEN -> createGame();
            case SPIEL_BEITRETEN -> joinGame();
            case BESTENLISTE -> showScoreboard();
            case LOGOUT -> logout();
            default -> System.out.println("Fehlerhafte Auswahl einer Funktion!");
        }
    }

    private void createGame(){
        gameLogic = new GameLogic(client_rmi, username);
        System.out.println("Spiel erstellt ... \n ... Join Code: " + client_rmi.getUserId(username));
        gameLogic.createGame();
    }

    private void joinGame(){
        gameLogic = new GameLogic(client_rmi, username);
        System.out.println("Join Code eingeben:");
        int joinCodeEntry = input.nextInt();
        gameLogic.joinGame(joinCodeEntry);
    }

    private void showScoreboard(){
        System.out.println("Bestenliste:");
        client_rmi.ShowScoreBoardAll();
    }

    private void loginFunctions(int funktion){
        input.nextLine();
        switch (funktion){
            case LOGIN:
                login();
                break;
            case SIGNUP:
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
        int versuche = 0;
        String pw;

        System.out.println("Benutzername: ");
        username = input.nextLine();
        System.out.println("Passwort: ");
        pw = input.nextLine();
        Boolean log = client_rmi.login(username, pw);
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

    private void signup() {
        input.nextLine();
        String username;
        String pw;
        boolean erg;
        System.out.println("Benutzername: ");
        username = input.nextLine();
        erg = client_rmi.userLoginExists(username);
        if (erg) {
            logger.warn("Benutzername bereits vergeben! \nBitte versuchen Sie es erneut.");
            // TODO Bessere Fehlerbehandlung im Falle eines vergebenen Benutzernames
            signup();
        } else {
            System.out.println("Passwort: ");
            pw = input.nextLine();
            erg = client_rmi.createLoginData(username, pw);
            if (!erg) {
                logger.error("Login fehlgeschlagen!\nVersuchen Sie es erneut.");
            }
        }
        setAuthenticated(false);
    }

    private void logout(){
        setAuthenticated(false);
        System.out.println("Benutzer wird abgemeldet.");
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
