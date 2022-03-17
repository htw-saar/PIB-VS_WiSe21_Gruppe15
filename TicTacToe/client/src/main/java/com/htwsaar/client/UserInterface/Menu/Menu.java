package com.htwsaar.client.UserInterface.Menu;

import com.htwsaar.client.UserInterface.TicTacToe.GameLogic;
import com.htwsaar.client.RMI.Client_RMI;
import org.apache.commons.codec.digest.DigestUtils;
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
    private final int SPIEL_REMATCH = 4;
    private final int LOGOUT = 9;
    private int funktion = -1;
    private final Client_RMI client_rmi;
    private final Scanner input = new Scanner(System.in);
    private String username;
    private GameLogic gameLogic;
    private Boolean rematchOption = false;

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
        if (isAuthenticated) {
            printInGame(format);
        } else {
            printLogin(format);
        }

        System.out.println(spacer);

        return intEinlesen();
    }

    private void printInGame(String format) {
        System.out.printf((format) + "%n", "|", SPIEL_ERSTELLEN, "|", "Spiel erstellen", "|");
        System.out.printf((format) + "%n", "|", SPIEL_BEITRETEN, "|", "Spiel beitreten", "|");
        System.out.printf((format) + "%n", "|", BESTENLISTE, "|", "Bestenliste anzeigen", "|");
        if (rematchOption) {
            System.out.printf((format) + "%n", "|", SPIEL_REMATCH, "|", "Letztes Spiel erneut spielen", "|");
        }
        System.out.printf((format) + "%n", "|", LOGOUT, "|", "Ausloggen", "|");
    }

    private void printLogin(String format) {
        System.out.printf((format) + "%n", "|", LOGIN, "|", "Einloggen", "|");
        System.out.printf((format) + "%n", "|", SIGNUP, "|", "Registrieren", "|");
        System.out.printf((format) + "%n", "|", ENDE, "|", "Beenden", "|");
    }

    private void ausfuehrenFunktion(int funktion) {
        if (isAuthenticated) {
            gameFunctions(funktion);
        } else {
            loginFunctions(funktion);
        }
    }

    private void gameFunctions(int funktion) {
        switch (funktion) {
            case SPIEL_ERSTELLEN -> createGame();
            case SPIEL_BEITRETEN -> joinGame();
            case BESTENLISTE -> showScoreboard();
            case SPIEL_REMATCH -> rematchGame();
            case LOGOUT -> logout();
            case ENDE -> System.exit(0);
            default -> System.out.println("Fehlerhafte Auswahl einer Funktion!");
        }
    }

    private void loginFunctions(int funktion) {
        input.nextLine();
        switch (funktion) {
            case LOGIN -> readLoginData();
            case SIGNUP -> signup();
            case ENDE -> System.exit(0);
            default -> logger.error("Fehlerhafte Auswahl einer Funktion!");
        }
    }

    private void createGame() {
        rematchOption = false;
        gameLogic = new GameLogic(client_rmi, username);
        System.out.println("Spiel erstellt ... \n ... Join Code: " + client_rmi.getUserId(username));
        gameLogic.createGame();
        rematchOption = true;
    }

    private void joinGame() {
        rematchOption = false;
        gameLogic = new GameLogic(client_rmi, username);
        System.out.println("Join Code eingeben:");
        int joinCodeEntry = input.nextInt();
        gameLogic.joinGame(joinCodeEntry);
        rematchOption = true;
    }

    private void showScoreboard() {
        System.out.println("Bestenliste:");
        client_rmi.ShowScoreBoardAll();
    }

    private void rematchGame() {
        gameLogic = new GameLogic(client_rmi, username);
        System.out.println("Letztes Spiel wird erneut gespielt ...");
        gameLogic.rematchGame();
    }

    private void readLoginData() {
        username = null;
        int versuche = 0;
        String pw;
        Boolean log = false;
        System.out.println("Benutzername: ");
        username = hashing256(input.nextLine());
        while (!log) {
            System.out.println("Passwort: ");
            pw = hashing256(input.nextLine());
            log = login(username, pw);
            if (log && versuche < 3) {
                System.out.println("Login war erfolgreich!");
                setAuthenticated(true);
            } else if (!log && versuche < 3) {
                logger.error("Login fehlgeschlagen!\nVersuchen Sie es erneut.");
                versuche++;
            } else {
                logger.error("Login fehlgeschlagen!\nLogin wurde gesperrt!");
                break;
            }
        }
    }

    //Alpha methode (User kann noch nicht angelegt werden)
    private Boolean login(String username, String password) {
        return client_rmi.login(username, password);
    }

    private void signup() {
        String username;
        String pw;
        boolean erg;
        System.out.println("Benutzername: ");
        username = hashing256(input.nextLine());
        erg = client_rmi.userLoginExists(username);
        if (erg) {
            logger.warn("Benutzername bereits vergeben! \nBitte versuchen Sie es erneut.");
            signup();
        } else {
            System.out.println("Passwort: ");
            pw = hashing256(input.nextLine());
            erg = client_rmi.createLoginData(username, pw);
            if (!erg) {
                logger.error("Login fehlgeschlagen!\nVersuchen Sie es erneut.");
            }
        }
        setAuthenticated(false);
    }

    private void logout() {
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

    private String hashing256(String text){
       return DigestUtils.sha256Hex(text);
    }
}
