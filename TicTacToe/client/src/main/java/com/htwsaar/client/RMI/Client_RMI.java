package com.htwsaar.client.RMI;

import com.htwsaar.server.Game.TicTacToe;
import com.htwsaar.server.RMI.ServerClient_Connect_Interface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Client_RMI {
    private static final Logger logger = LogManager.getLogger(Client_RMI.class);
    private static final String HOST_IP = "simon.selfhost.co";
    private static final int PORT = 42424;
    private static final String REGISTRY = "GAME";

    private ServerClient_Connect_Interface clientStub;
    private String loggedInUser;

    /**
     * Konstruktor der Client_RMI Klasse
     */
    public Client_RMI() {
        init_RMI();
        logger.debug("Verbindung zum Server wurde erfolgreich hergestellt!");
    }

    /**
     * Die Methode init_RMI versucht eine VErbindung zum Server herzustellen und
     * startet einen neuen Versuch alle 2 Sekunden
     */
    private void init_RMI() {
        while (clientStub == null) {
            clientStub = connectToServer();
            if (clientStub == null) {
                System.out.println("Verbindung zum Server fehlgeschlagen!");
                System.out.println("NÃ¤chster Versuch startet gleich...");
                waitOn(2);
            }
        }
    }

    /**
     * Eine Methode zum ueberpruefen ob der Benutzer existiert
     *
     * @param name der zu ueberpruefende Name
     * @return true wennn der Benutzer existiert andernfalls false
     */
    public Boolean userLoginExists(String name) {
        try {
            return clientStub.userLoginExists(name);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return false;
        }
    }

    /**
     * Eine Methode die einen neuen Benutzer anlegt
     *
     * @param name     der Name des neuen Benutzers
     * @param password das Passwort des neuen Benutzers
     * @return true wennn der Benutzer erfolgreich angelegt wurde
     */
    public Boolean createLoginData(String name, String password) {
        try {
            return clientStub.createLoginData(name, password);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return false;
        }
    }

    /**
     * Gibt den Benutzernamen des eingelogten Spielers zurueck
     *
     * @return Der Name des Benutzers
     */
    public String getLoggedInUser() {
        try {
            return loggedInUser;
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return "logged in user not found";
        }
    }

    /**
     * Gibt die ID eines Spielers zurueck
     *
     * @param username Der Name des Benutzers desen ID gesucht wird
     * @return Die ID des Benutzers
     */
    public int getUserId(String username) {
        try {
            return clientStub.getUserId(username);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return -1;
        }
    }

    /**
     * Ein Rematch gegen einen Benutzer starten
     *
     * @param username Der Name des Benutzers zum rematchen
     * @return true wenn das Rematch stattfinden kann
     */
    public Boolean rematchGame(String username) {
        try {
            return clientStub.rematchGame(username);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return false;
        }
    }

    /**
     * Ein Spiel starten mit dem Benutzer als erster Spieler
     *
     * @param username Der Name des Benutzers der Spieler 1 wird
     * @return der preshared Key
     */
    public String createGame(String username) {
        try {
            return clientStub.createGame(username);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return null;
        }
    }

    /**
     * Gibt das Gameboard zurueck an dem der Benutzer teil nimmt
     *
     * @param username Der Name des Benutzers dessen Gameboard geladen werden soll
     * @return das Gameboard als 2D-Array
     */
    public String[] returnGameboard(String username) {
        try {
            return clientStub.returnGameboard(username);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return null;
        }
    }

    /**
     * Fuegt den Spieler dem Spiel mit dem passenden join Code hinzu
     *
     * @param username Der Spieler der hinzugefuegt werden soll
     * @param joinCode Der join Code des Spieles
     * @return der preshared Key
     */
    public String joinGame(int joinCode, String username) {
        try {
            return clientStub.joinGame(username, joinCode);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return null;
        }
    }

    /**
     * Setzt das Feld mit dem Symbol des Spielers
     *
     * @param pos          Position des zu setzenden Feldes im Gameboard
     * @param username     Der Benutzer der das Feld setzt
     * @param preSharedKey zur authentifizierung des Spielers
     * @return Enum mit dem Game Status
     */
    public TicTacToe.Winner setField(String username, int pos, String preSharedKey) {
        try {
            return clientStub.setField(username, pos, preSharedKey);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return TicTacToe.Winner.NONE;
        }
    }

    /**
     * Testet die Login Daten
     *
     * @param userName Der Benutzername
     * @param password Das Passwort des Benutzers
     * @return true wenn die Daten stimmen
     */
    private boolean testLoginData(String userName, String password) {
        try {
            return clientStub.sendLoginData(userName, password);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return false;
        }
    }

    /**
     * Gibt das gesamte Scoreboard aus
     */
    public void ShowScoreBoardAll() {
        try {
            String format = " %2s %12s %2s %6s %2s %6s %2s %6s %2s";
            String spacer = "  +---------------+---------+---------+---------+";
            System.out.println(spacer);
            System.out.printf((format) + "%n", "|", "Username", "|", "Wins", "|", "Loses", "|", "Score", "|");
            System.out.println(spacer);
            List<String> stringList;
            stringList = clientStub.scoreboardRequest();
            if (stringList != null) {
                for (String score : stringList) {
                    System.out.println(score);
                }
            }
            System.out.println(spacer);
        } catch (Exception e) {
            logger.error("Client exception: " + e);
        }
    }

    /**
     * Gibt die Statistiken eines einzelnen Benutzers aus
     *
     * @param username Der Benutzername desen Statistiken angezeigt werden sollen
     * @return 1 wenn es funktioniert hat 0 wenn es ein Fehler gab
     */
    public int ShowOwnStats(String username) {
        try {
            System.out.println(clientStub.scoreboardRequestForUser(username));
            return 1;
        } catch (Exception e) {
            logger.error("Client exception: " + e);
            return 0;
        }
    }

    /**
     * Baut die Verbindung zum Server auf
     *
     * @return Der stub bei einer erfolgreichen Verbindung sonst null
     */
    private ServerClient_Connect_Interface connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry(HOST_IP, PORT);
            ServerClient_Connect_Interface stub = (ServerClient_Connect_Interface) registry.lookup(REGISTRY);
            System.out.println("Server Verbindung besteht!\n");
            System.out.println(stub);
            return stub;
        } catch (NotBoundException | RemoteException e) {
            logger.error("Client exception: " + e);
            return null;
        }
    }

    /**
     * Eine Methode zum einlogen des Benutzers
     *
     * @param username Der Benutzername
     * @param password Das Passwort des Benutzers
     * @return true wenn die Login Daten stimmen sonst false
     */
    public Boolean login(String username, String password) {
        loggedInUser = username;
        return testLoginData(username, password);
    }

    /**
     * Ueberprueft ob das Spield es Benutzers schon gestartet ist
     *
     * @param username der Benutzer desen Status ueberprueft werden soll
     * @param listTyp  Der Name der Liste in der gesucht werden soll
     * @return true wenn es gestartet ist sonst false
     */
    public Boolean checkGameStart(String username, String listTyp) {
        try {
            return clientStub.checkGameStart(username, listTyp);
        } catch (RemoteException e) {
            logger.error("Client exception: " + e);
            return null;
        }

    }

    /**
     * Gibt den aktiven Spieler zurueck
     *
     * @param username der Benutzer in desen Spiel der aktive Spieler gesucht wird
     * @return Der Name des aktiven Spielers
     */
    public String getActivePlayer(String username) {
        try {
            return clientStub.getActivePlayer(username);
        } catch (RemoteException e) {
            logger.error("Client exception: " + e);
            return null;
        }
    }

    /**
     * Gibt den aktiven Spieler zurueck
     *
     * @param username der Benutzer in desen Spiel der aktive Spieler gesucht wird
     * @return Der Name des aktiven Spielers
     */
    public TicTacToe.Winner getGameStatus(String username) {
        try {
            return clientStub.getGameStatus(username);
        } catch (RemoteException e) {
            logger.error("Client exception: " + e);
            return null;
        }
    }

    /**
     * Eine Methode um eine gewisse Zeit zu warten
     *
     * @param sec Die Anzahl der Sekunden die gewartet werden soll
     */
    private void waitOn(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
