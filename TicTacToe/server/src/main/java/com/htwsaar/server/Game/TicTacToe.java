package com.htwsaar.server.Game;

import com.htwsaar.server.Hibernate.dao.UserDao;
import com.htwsaar.server.Hibernate.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        UNSETTELD("unsetteld");

        public final String label;

        Winner(String label) {
            this.label = label;
        }
    }

    private String x;
    private String o;
    private String[] gameboard = new String[9];
    private final int[][] winConditions;
    private String[] players = new String[2];
    private int joinCode;

    /**
     * Konstruktor von TicTacToe
     * Erstellt die Win Konditionen und initialisiert das Spielbrett
     */
    public TicTacToe(String username) {
        winConditions = new int[][]{{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9},
                {1, 5, 9},
                {3, 5, 7}};
        initGameboard();
        setX(username);
        createJoinCode(username);
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
        players[0] = name;
    }

    /**
     * Setzt den Username der zu X gehört
     *
     * @param name enthält den Username
     */
    public void setO(String name) {
        o = name;
        players[1] = name;
    }

    /**
     * Setzt einen Spielermarker an Position pos
     *
     * @param player der Spieler X oder O
     * @param pos    die Position wo der Marker gesetzt wird
     */
    public Winner setField(Winner player, int pos) {
        gameboard[pos] = player.label;
        return checkWinCondition(player);
    }

    /**
     * Initialisiert das Spielbrett
     *
     * @return String[] Gibt ein Array zurück mit den Zahlen des Spielfeldes(das Spielbrett)
     */
    private String[] initGameboard() {
        for (int i = 0; i < gameboard.length; i++) {
            int field = i + 1;
            this.gameboard[i] = field + "";
        }
        return this.gameboard;
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
        UserDao userDao = new UserDao();
        User user = userDao.getUser(username);
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

    public int comparePlayerX(String username) {
        if (x == username) {
            return 1;
        }
        return 0;
    }

    public int comparePlayerO(String username) {
        if (o == username) {
            return 1;
        }
        return 0;
    }
}
