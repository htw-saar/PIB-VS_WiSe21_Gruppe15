package com.htwsaar.client.UserInterface.Menu;

import com.htwsaar.client.UserInterface.TicTacToe.GameLogic;
import com.htwsaar.client.RMI.Client_RMI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private String username;
    int versuche = 0;
    private final Client_RMI client_rmi;
    private final Scanner input = new Scanner(System.in);

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
        System.out.println(String.format(format, "|", "Nummer", "|", "Funktion", "|"));
        System.out.println(spacer);
        if (isAuthenticated){
            System.out.println(String.format(format, "|", SPIEL_ERSTELLEN, "|", "Spiel erstellen", "|"));
            System.out.println(String.format(format, "|", SPIEL_BEITRETEN, "|", "Spiel beitreten", "|"));
            System.out.println(String.format(format, "|", BESTENLISTE, "|", "Bestenliste anzeigen", "|"));
            System.out.println(String.format(format, "|", LOGOUT, "|", "Ausloggen", "|"));
        } else {
            System.out.println(String.format(format, "|", LOGIN, "|", "Einloggen", "|"));
            System.out.println(String.format(format, "|", SIGNUP, "|", "Registrieren", "|"));
            System.out.println(String.format(format, "|", ENDE, "|", "Beenden", "|"));
        }

        System.out.println(spacer);

        return intEinlesen();
    }

    private void ausfuehrenFunktion(int funktion) {
        if (isAuthenticated){
            switch (funktion) {
                case SPIEL_ERSTELLEN:
                    System.out.println("Spiel erstellen:");
                    GameLogic.startGame();
                    break;
                case SPIEL_BEITRETEN:
                    System.out.println("Spiel beitreten:");
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
        } else {
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
        System.out.println("\n\n\n\n");
    }

    //Alpha methode (User kann noch nicht angelegt werden)
    private void login() {
        input.nextLine();
        String pw;
        Boolean log;
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
