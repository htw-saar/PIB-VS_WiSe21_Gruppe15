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
    private final ArrayList<TicTacToe> rematchGames = new ArrayList<>();
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
     * @param name     Name des neuen Users
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
     * Ueberprueft ob das Spiel des Users schon gestartet ist oder noch in der gesuchten Gameslist ist
     *
     * @param username Der Name desen Spielstatus ueberprueft werden soll
     * @param listTyp  Der Name der Liste in der das Game gesucht wird
     * @return true wenn es gestartet ist und nicht mehr in der gesuchten Gameslist
     */
    public Boolean checkGameStart(String username, String listTyp) {
        int gameNumber = -1;
        switch (listTyp) {
            case "Wait":
                gameNumber = playerInWhichGame(waitingGames, username);
                break;
            case "Rematch":
                gameNumber = playerInWhichGame(rematchGames, username);
                if (gameNumber != -1) {
                    rematchGames.get(gameNumber).setRematch(username);
                    if (rematchGames.get(gameNumber).isRematchReady()) {
                        games.add(rematchGames.get(gameNumber));
                        rematchGames.remove(gameNumber);
                    }
                }
                break;
        }
        if (gameNumber != -1) {
            return false;
        }
        return true;
    }

    /**
     * Startet den Serverseitigen RMI Stub
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
     * @param name     Name des Nutzers
     * @param password Passwort des Nutzers
     * @return true wenn der login erfolgreich war
     */
    public Boolean sendLoginData(String name, String password) {
        User user = databaseService.getUserData(name);
        if (user != null) {
            if (name.equals(user.getUsername())){
                return password.equals(user.getPassword());
            }
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
        game = new TicTacToe(username, databaseService);
        waitingGames.add(game);
        return true;
    }

    /**
     * Löscht ein beendetes Spiel aus finishedGames und fügt es rematchGames hinzu
     *
     * @param username Der Nutzer der ein Rematch starten will
     * @return true wenn das rematch der Rematch liste hinzugefuegt wurde
     */
    public Boolean rematchGame(String username) {
        int gameNumber = playerInWhichGame(finishedGames, username);
        if (gameNumber != -1) {
            rematchGames.add(finishedGames.get(gameNumber));
            finishedGames.remove(gameNumber);
        }
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
        if (gameId >= 0) {
            gameboard = games.get(gameId).getGameboard();
        } else {
            gameId = playerInWhichGame(finishedGames, username);
            gameboard = finishedGames.get(gameId).getGameboard();
        }
        return gameboard;
    }

    /**
     * Gibt die User ID des Benutzers zurueck
     *
     * @param username Der Benutzer dessen Id zurueckgegeben werden soll
     * @return Die User ID des Benutzers
     */
    public int getUserId(String username) {
        int userId;
        User user = databaseService.getUserData(username);
        userId = user.getUserId();
        return userId;
    }

    /**
     * Eine Methode die einen Spieler einem gewuenschten Spiel hinzufuegt
     *
     * @param username Der User der dem Spiel beitreten soll
     * @param joinCode Der join Code des Spieles
     * @return true wenn der Spieler dem Spiel hinzugefuegt wurde
     */
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

    /**
     * Eine Methode die den Spieler aus einem fertigen Spiel entfernt
     *
     * @param username Der Spieler der entfernt werden soll
     */
    private void deleteOldGames(String username) {
        int gameID = playerInWhichGame(finishedGames, username);
        if (gameID >= 0) {
            finishedGames.remove(gameID);
        }
    }

    /**
     * Die Methode setField setzt das Feld an der gewuenschten Position mit dem Symbol des Benutzers
     *
     * @param username Der Benutzer der das Feld setzten moechte
     * @param pos      Die Position des zu setzenden Feldes
     * @return Das Enum Winner mit dem aktuellen Spielestatus
     */
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

    /**
     * Die Methode ueberprueft ob das Spiel zu ende ist und verteilt die
     * Wins und Loses an die jeweiligen Spieler
     *
     * @param gameId    Die ID des Spieles
     * @param players   Die beiden Spieler
     * @param gameState Der Zustand des Spieles
     */
    private void checkGameEnd(int gameId, String[] players, TicTacToe.Winner gameState) {
        if (!gameState.equals(TicTacToe.Winner.NONE)) {
            if (!gameState.equals(TicTacToe.Winner.FIELDSET)) {
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

    /**
     * Schaut nach an in welchem Spiel der gesuchte Benutzer sich befindet
     * und gibt den dazugehoerigen index zurueck
     *
     * @param list     Die zu durchsuchende Liste
     * @param username Der zu suchende Benutzer
     * @return Der Index an dem sich der Benutzer befindet oder -1 wenn der Nutzer nicht gefunden wurde
     */
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

    /**
     * Eine Methode um herauszufinden wer aktuell der Aktive Spieler im Spiel ist
     *
     * @param username Der Benutzer desen Spiel ueberprueft werden soll
     * @return Der aktive Spieler als String
     */
    public String getActivePlayer(String username) {
        int gameID = playerInWhichGame(games, username);
        if (gameID >= 0) {
            return games.get(gameID).getActivePlayer();
        } else {
            gameID = playerInWhichGame(finishedGames, username);
            return finishedGames.get(gameID).getActivePlayer();
        }
    }

    /**
     * Eine Methode die den aktuellen Status des Spieles zurueck gibt
     *
     * @param username Der Benutzer desen Spiel ueberprueft werden soll
     * @return Das Enum Winner mit dem aktuellen Status des Spieles
     */
    public TicTacToe.Winner getGameStatus(String username) {
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

