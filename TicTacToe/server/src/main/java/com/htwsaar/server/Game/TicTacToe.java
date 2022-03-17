package com.htwsaar.server.Game;

import com.htwsaar.server.Hibernate.entity.User;
import com.htwsaar.server.Services.DatabaseService;

import java.util.Objects;

/**
 * Die TicTacToe Klasse enthält die gesamte Spiellogic die auf dem Server ausgeführt wird.
 *
 * @author Mario, Simon, Alex, Oliver, Ahmad und Maximilian
 * @version 1.0
 */
public class TicTacToe {

    public enum Winner {
        Player1("X"),
        Player2("O"),
        NONE("none"),
        UNSETTELD("unsetteld"),
        FIELDSET("fieldset");

        public final String label;

        Winner(String label) {
            this.label = label;
        }
    }

    private DatabaseService databaseService;
    private String x;
    private Boolean xRematch = false;
    private Boolean oRematch = false;
    private int readyPlayers = 0;
    private final String[] gameboard = new String[9];
    private final int[][] winConditions;
    private final String[] players = new String[2];
    private int joinCode;
    private String activePlayer;
    private Winner gameStatus;

    /**
     * Konstruktor von TicTacToe
     * Erstellt die Win Konditionen und initialisiert das Spielbrett
     */
    public TicTacToe(String username, DatabaseService databaseService) {
        this.databaseService = databaseService;
        setGameStatus(Winner.NONE);
        winConditions = new int[][]{
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {0, 4, 8},
                {2, 4, 6}};
        initGameboard();
        setX(username);
        createJoinCode(username);
    }

    public int getJoinCode() {
        return joinCode;
    }

    public void setRematch(String username) {
        if (x.equals(username)) {
            xRematch = true;
        } else {
            oRematch = true;
        }
    }

    public Boolean isRematchReady() {
        if (xRematch && oRematch) {
            setGameStatus(Winner.NONE);
            initGameboard();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gibt die Playernamen des Spiels zurück
     *
     * @return String[] Spielernamen des Spiels
     */
    public String[] getPlayers() {
        return players;
    }

    /**
     * Setzt den Username der zu X gehört
     *
     * @param name enthält den Username
     */
    public void setX(String name) {
        x = name;
        players[0] = x;
        activePlayer = x;
    }

    /**
     * Setzt den Username der zu X gehört
     *
     * @param name enthält den Username
     */
    public void setO(String name) {
        players[1] = name;
    }

    /**
     * Setzt den Active Player
     *
     * @param activePlayer enthält den Username
     */
    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * Gibt Active Player zurueck
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * Setzt einen Spielermarker an Position pos
     *
     * @param pos die Position wo der Marker gesetzt wird
     */
    public Winner setField(int pos) {
        if (gameboard[pos].equals(Winner.Player1.label) || gameboard[pos].equals(Winner.Player2.label)) {
            return Winner.FIELDSET;
        }

        Winner player = getPlayerSymbol();
        gameboard[pos] = player.label;
        Winner winner = checkWinCondition(player);
        setGameStatus(winner);
        return winner;
    }

    /**
     * Initialisiert das Spielbrett
     *
     * @return String[] Gibt ein Array zurück mit den Zahlen des Spielfeldes(das Spielbrett)
     */
    private void initGameboard() {
        for (int i = 0; i < gameboard.length; i++) {
            int field = i + 1;
            this.gameboard[i] = field + "";
        }
    }

    public String[] getGameboard() {
        return gameboard;
    }


    /**
     * Die Methode checkWinCondition ueberprueft ob der Spieler der zuletzt am Zug war eine
     * Tik-Tak-Toe Win-Condition erfuellt.
     * Sollte dies der Fall sein wird der Spieler "X" oder "O" zurueckgegeben andernfalls
     * wird die Methode checkGameboardFull ausgeführt und ein Return von "none" oder "unsetteld"
     * ausgegeben
     *
     * @param player Der Spieler der zuletzt am Zug war
     * @return Ein Gewinner in Form von "O" oder "X", oder ob das Spiel weiter geht "none"/beendet "unsetteld" wird
     */
    public Winner checkWinCondition(Winner player) {
        for (int[] winCondition : winConditions) {
            if (Objects.equals(gameboard[winCondition[0]], gameboard[winCondition[1]])) {
                if (Objects.equals(gameboard[winCondition[1]], gameboard[winCondition[2]])) {
                    return player;
                }
            }
        }
        return checkGameboardFull();
    }

    /**
     * Die Methode checkGameboardFull ueberprueft alle Felder die im Gameboard-Array beinhaltet sind
     * und  schaut nach ob sich noch eine Zahl von 1-9 darin befindet.
     * Sollte dies der Fall sein wird "none" als Winner zurueckgegeben andernfalls ist das Gameboard
     * voll und somit wird als Rueckgabewert "unsetteld" ausgegeben.
     *
     * @return entscheidung ob das Gameboard voll ist ("unsetteld") oder es noch unbesetzte Felder gibt ("none")
     */
    private Winner checkGameboardFull() {
        for (int i = 0; i < gameboard.length; i++) {
            int field = i + 1;
            if (gameboard[i].equals(field + "")) {
                return Winner.NONE;
            }
        }
        return Winner.UNSETTELD;
    }

    /**
     * Die Methode createJoinCode erstellt einen JoinCode aus der UserID
     */
    private void createJoinCode(String username) {
        User user = databaseService.getUserData(username);
        if (user != null) {
            joinCode = user.getUserId();
        }
    }

    /**
     * Die Methode compareJoinCode vergleicht ob es sich bei dem erhaltenen Code um den eigenen handelt
     *
     * @param joinCode Der erhaltene Code zum vergleichen
     * @return 1 wenn die Codes uebereinstimmen und 0 wenn sie ungleich sind
     */
    public int compareJoinCode(int joinCode) {
        if (joinCode == this.joinCode) {
            return 1;
        } else {
            return 0;
        }
    }

    public Winner getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(Winner gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void switchActivePlayer() {
        if (activePlayer.equals(players[0])) {
            setActivePlayer(players[1]);
        } else {
            setActivePlayer(players[0]);
        }
    }

    private Winner getPlayerSymbol() {
        if (x.equals(activePlayer)) {
            return Winner.Player1;
        } else {
            return Winner.Player2;
        }
    }
}
