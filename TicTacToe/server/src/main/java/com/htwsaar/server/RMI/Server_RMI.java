package com.htwsaar.server.RMI;
import com.htwsaar.server.Game.TicTacToe;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;

import com.htwsaar.server.Hibernate.entity.User;
import com.htwsaar.server.Services.DatabaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server_RMI implements ServerClient_Connect_Interface {

    private static final Logger logger = LogManager.getLogger(Server_RMI.class);
    private static final int PORT = 42424;
    private static final String REGISTRY = "GAME";
    private final DatabaseService databaseService;

    private final ArrayList<TicTacToe> games = new ArrayList<>();
    private final ArrayList<TicTacToe> finishedGames = new ArrayList<>();
    private final ArrayList<TicTacToe> waitingGames = new ArrayList<>();

    /**
     * Konstruktor fuer die Klasse Server_RMI
     * 
     * @param databaseService Das Objekt fuer die Datenbankanbindung
     */
    public Server_RMI(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Eine Methode zum erstellen eines neuen Users in der Datenbank
     * 
     * @param name Name des neuen Users
     * @param password Passwort des neuen Users
     * @return true wenn der User erfolgreich erstellt wurde
     */
    public Boolean createLoginData(String name, String password) {
            databaseService.addUser(name, password);
            return true;
    }

    /**
     * Ueberprueft ob der uebergebene Name zu einem gueltigen login gehoert
     * 
     * @param name Der Name der auf seine gueltigkeit ueberprueft werden soll
     * @return true wenn der Name zu einem Nutzer gehoert
     */
    public Boolean userLoginExists(String name) {
        return databaseService.getUserData(name) != null;
    }

    /**
     * Ueberprueft ob das Spiel des Users schon gestartet ist oder noch ein waiting Games ist
     * 
     * @param username Der Name desen Spielstatus ueberprueft werden soll
     * @return false wenn das noch ein waiting Game ist und true wenn es gestartet ist
     */
    public Boolean checkGameStart(String username) {
        User user = databaseService.getUserData(username);
        for (TicTacToe game : waitingGames) {
            if (user.getUserId() == game.getJoinCode()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     */
    public void start_Server_RMI() {
        try {
            Server_RMI obj = new Server_RMI(databaseService);
            ServerClient_Connect_Interface stub = (ServerClient_Connect_Interface) UnicastRemoteObject.exportObject(obj, 0);
            LocateRegistry.createRegistry(PORT);
            Registry registry = LocateRegistry.getRegistry(PORT);
            registry.rebind(REGISTRY, stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            logger.error("Server exception: " + e);
        }
    }

    /**
     * Eine Methode zum anmelden des Users 
     * 
     * @param name Name des Nutzers
     * @param password Passwort des Nutzers
     * @return true wenn der login erfolgreich war
     */
    public Boolean sendLoginData(String name, String password) {
            User user = databaseService.getUserData(name);
            if (user != null) {
                return password.equals(user.getPassword());
            }
            return false;
    }

    /**
     * Gibt das Scoreboard zurueck
     * 
     * @return Das Scoreboard als stringlist 
     */
    public List<String> scoreboardRequest() {
            String format = " %2s %12s %2s %6s %2s %6s %2s %6s %2s";
            List<String> stringList = new ArrayList<>();
            for (User user : databaseService.getScoreboard()) {
                String print = String.format(format, "|", user.getUsername(), "|", user.getWins(), "|", user.getLoses(), "|", user.getScore(), "|");
                stringList.add(print);
            }
            return stringList;
    }

    /**
     * Die Scoreboarddaten fuer den einzelnen Benutzer
     * 
     * @param name Der Name des Nutzers desen Daten erfasst werden sollen
     * @return Die Daten des Nutzers als String
     */
    public String scoreboardRequestForUser(String name) {
            User user = databaseService.getUserData(name);
            if (user != null) {
                return user.toString();
            } else {
                return null;
            }
    }

    /**
     * Erzeugt ein neues Spiel mit dem Benutzer als Spieler 1
     * 
     * @param username Der Nutzer der der Besitzer des neuen Spieles werden soll
     * @return true wenn das Spiel erfolgreich erstellt wurde
     */
    public Boolean createGame(String username) {
            TicTacToe game;
            game = new TicTacToe(username);
            waitingGames.add(game);
            return true;
    }

    /**
     * Gibt das aktuelle Gameboard des Benutzers zurueck
     * 
     * @param username Der Name des Spielers dessen Gameboard geladen werden soll
     * @return Das aktuelle Gameboard des Benutzers als String Array
     */
    public String[] returnGameboard(String username) {
        String[] gameboard;
        int gameId = playerInWhichGame(games, username);
        if (gameId >= 0){
            gameboard = games.get(gameId).getGameboard();
        } else {
            gameId = playerInWhichGame(finishedGames, username);
            gameboard = finishedGames.get(gameId).getGameboard();
        }
        return gameboard;
    }

    public int getUserId(String username) {
        int userId;
        User user = databaseService.getUserData(username);
        userId = user.getUserId();
        return userId;
    }

    public Boolean joinGame(String username, int joinCode) {
            for (int i = 0; i < waitingGames.size(); i++) {
                if (waitingGames.get(i).compareJoinCode(joinCode) == 1) {
                    waitingGames.get(i).setO(username);
                    deleteOldGames(username);
                    games.add(waitingGames.get(i));
                    waitingGames.remove(i);
                    return true;
                }
            }
            return false;
    }

    private void deleteOldGames(String username){
        int gameID = playerInWhichGame(finishedGames, username);
        if (gameID >= 0){
            finishedGames.remove(gameID);
        }
    }

    public TicTacToe.Winner setField(String username, int pos) {
            int gameNumber;
            String[] players;
            gameNumber = playerInWhichGame(games, username);
            if (gameNumber != -1) {
                TicTacToe game = games.get(gameNumber);
                players = game.getPlayers();
                TicTacToe.Winner gameState = games.get(gameNumber).setField(pos);
                checkGameEnd(gameNumber, players, gameState);
                return gameState;
            }
            logger.error(("SetField: Kein Spiel gefunden"));
            return TicTacToe.Winner.NONE;
    }

    private void checkGameEnd(int gameId, String[] players, TicTacToe.Winner gameState){
        if (!gameState.equals(TicTacToe.Winner.NONE)) {
            if (!gameState.equals(TicTacToe.Winner.FIELDSET)){
                games.get(gameId).switchActivePlayer();
                finishedGames.add(games.get(gameId));
                games.remove(gameId);
            }
            if (gameState.equals(TicTacToe.Winner.Player1)) {
                databaseService.addLose(players[1]);
                databaseService.addWin(players[0]);
            } else if (gameState.equals(TicTacToe.Winner.Player2)) {
                databaseService.addLose(players[0]);
                databaseService.addWin(players[1]);
            }
        } else {
            games.get(gameId).switchActivePlayer();
        }
    }

    private int playerInWhichGame(ArrayList<TicTacToe> list, String username) {
        String[] playerNames;
        for (int i = 0; i < list.size(); i++) {
            playerNames = list.get(i).getPlayers();
            if (playerNames[0].equals(username) || playerNames[1].equals(username)) {
                return i;
            }
        }
        return -1;
    }

    public String getActivePlayer(String username) {
        int gameID = playerInWhichGame(games, username);
        if (gameID >= 0){
           return games.get(gameID).getActivePlayer();
        } else {
            gameID = playerInWhichGame(finishedGames, username);
            return finishedGames.get(gameID).getActivePlayer();
        }
    }

    public TicTacToe.Winner getGameStatus(String username){
        int gameID = playerInWhichGame(games, username);
        TicTacToe.Winner status;
        if (gameID >= 0) {
            status = games.get(gameID).getGameStatus();
        } else {
            gameID = playerInWhichGame(finishedGames, username);
            status = finishedGames.get(gameID).getGameStatus();
        }
        return status;
    }

}

