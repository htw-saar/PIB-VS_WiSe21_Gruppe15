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
    private final Client_RMI client_rmi;
    private final Scanner input = new Scanner(System.in);
    private int funktion = -1;
    private String username;
    private GameLogic gameLogic;
    private Boolean rematchOption = false;

    /**
     * Konstruktor von Menu
     *
     * @param client_rmi rmi des Clienten
     */
    public Menu(Client_RMI client_rmi) {
        this.client_rmi = client_rmi;
    }

    /**
     * Startet das Auswahlmenue, laeuft solange nicht die Auswahl ENDE aufgerufen wird
     */
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

    /**
     * Legt das Format des Auswahlmenues fest und gibt die ausgewaehlte Funktion zurueck
     *
     * @return int Die Nummer der ausgewaehlten Funktion
     */
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

    /**
     * Gibt das Auswahlmenue nach dem Login aus
     *
     * @param format Format des Auswahlmenues
     */
    private void printInGame(String format) {
        System.out.printf((format) + "%n", "|", SPIEL_ERSTELLEN, "|", "Spiel erstellen", "|");
        System.out.printf((format) + "%n", "|", SPIEL_BEITRETEN, "|", "Spiel beitreten", "|");
        System.out.printf((format) + "%n", "|", BESTENLISTE, "|", "Bestenliste anzeigen", "|");
        if (rematchOption) {
            System.out.printf((format) + "%n", "|", SPIEL_REMATCH, "|", "Erneut spielen", "|");
        }
        System.out.printf((format) + "%n", "|", LOGOUT, "|", "Ausloggen", "|");
    }

    /**
     * Gibt das Auswahlmenue vor dem Login aus
     *
     * @param format Format des Auswahlmenues
     */
    private void printLogin(String format) {
        System.out.printf((format) + "%n", "|", LOGIN, "|", "Einloggen", "|");
        System.out.printf((format) + "%n", "|", SIGNUP, "|", "Registrieren", "|");
        System.out.printf((format) + "%n", "|", ENDE, "|", "Beenden", "|");
    }

    /**
     * Weiterleitung von Print der Funktion zu den Funktionen selbst
     *
     * @param funktion Die Nummer der ausgewaehlten Funktion
     */
    private void ausfuehrenFunktion(int funktion) {
        if (isAuthenticated) {
            gameFunctions(funktion);
        } else {
            loginFunctions(funktion);
        }
    }

    /**
     * Startet die Funktion die nach dem Login ausgewaehlt wurde
     *
     * @param funktion Die Nummer der ausgewaehlten Funktion
     */
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

    /**
     * Startet die Funktion die vor dem Login ausgewaehlt wurde
     *
     * @param funktion Die Nummer der ausgewaehlten Funktion
     */
    private void loginFunctions(int funktion) {
        input.nextLine();
        switch (funktion) {
            case LOGIN -> readLoginData();
            case SIGNUP -> signup();
            case ENDE -> System.exit(0);
            default -> logger.error("Fehlerhafte Auswahl einer Funktion!");
        }
    }

    /**
     * Erstellt ein Spiel über RMI
     */
    private void createGame() {
        rematchOption = false;
        if (gameLogic == null){
            gameLogic = new GameLogic(client_rmi, username);
        }
        System.out.println("Spiel erstellt ... \n ... Join Code: " + client_rmi.getUserId(username));
        gameLogic.createGame();
        rematchOption = true;
    }

    /**
     * Beitritt eines Spiels über einen Joincode
     */
    private void joinGame() {
        rematchOption = false;
        if (gameLogic == null){
            gameLogic = new GameLogic(client_rmi, username);
        }
        System.out.println("Join Code eingeben:");
        int joinCodeEntry = input.nextInt();
        gameLogic.joinGame(joinCodeEntry);
        rematchOption = true;
    }

    /**
     * Zeigt das Scoreboard an
     */
    private void showScoreboard() {
        System.out.println("Bestenliste:");
        client_rmi.ShowScoreBoardAll();
    }

    /**
     * Startet ein Rueckmatch des letzten Spiels
     */
    private void rematchGame() {
//        gameLogic = new GameLogic(client_rmi, username);
        System.out.println("Letztes Spiel wird erneut gespielt ...");
        gameLogic.rematchGame();
    }

    /**
     * Liest die Logindaten ein und sendet sie an den Server
     */
    private void readLoginData() {
        username = null;
        int versuche = 0;
        String pw;
        Boolean log = false;
        System.out.println("Benutzername: ");
        username = input.nextLine();
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

    /**
     * Sendet die Logindaten über RMI an den Clienten
     *
     * @param username Username des Nutzers
     * @param password Passwort des Nutzers
     * @return Gibt zurueck, ob der Login erfolgreich war
     */
    private Boolean login(String username, String password) {
        return client_rmi.login(username, password);
    }

    /**
     * Funktion zum Registrieren des Benutzers, sendet die Daten ueber RMI an den Server
     */
    private void signup() {
        String username;
        String pw;
        boolean erg;
        System.out.println("Benutzername: ");
        username = input.nextLine();
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

    /**
     * Funktion zum Abmelden des Benutzers
     */
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

    /**
     * Methode um einen Text zu hashen
     *
     * @param text Text der gehasht wird
     * @return Der gehashte Text
     */
    private String hashing256(String text) {
        return DigestUtils.sha256Hex(text);
    }
}
