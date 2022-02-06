package com.htwsaar.server.Game;

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

    private String[] gameboard = new String[9];
    private final int[][] winConditions;
    private int joinCode;

    /**
     * Konstruktor von TicTacToe
     * Erstellt die Win Konditionen und initialisiert das Spielbrett
     */
    public TicTacToe(){
        winConditions= new int[][]{{1, 2, 3},
                                    {4, 5, 6},
                                    {7,8,9},
                                    {1,4,7},
                                    {2,5,8},
                                    {3, 6, 9},
                                    {1, 5, 9},
                                    {3, 5, 7}};
        initGameboard();
        createJoinCode();
    }

    /**
     * Setzt einen Spielermarker an Position pos
     * @param player der Spieler X oder O
     * @param pos die Position wo der Marker gesetzt wird
     */
    private void setField(Winner player, int pos){
        gameboard[pos] = player.label;
    }

    /**
     * Initialisiert das Spielbrett
     * @return String[] Gibt ein Array zurück mit den Zahlen des Spielfeldes(das Spielbrett)
     */
    private String[] initGameboard(){
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
     * 
     * @return Ein Gewinner in Form von "O" oder "X", oder ob das Spiel weiter geht "none"/beendet "unsetteld" wird
     */
    public Winner checkWinCondition(Winner player){
        for (int[] winCondition : winConditions){
            if (Objects.equals(gameboard[winCondition[0]], gameboard[winCondition[1]])){
                if (Objects.equals(gameboard[winCondition[1]], gameboard[winCondition[2]])){
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
    private Winner checkGameboardFull(){
        for (int i = 0; i < gameboard.length; i++) {
            int field = i+1;
            if (gameboard[i].equals(field + "")){
                return Winner.NONE;
            }
        }
        return Winner.UNSETTELD;
    }

    private int createJoinCode() {
        joinCode = (int) ((Math.random() * (99999 - 10000)) + 10000);
    }

    public int compareJoinCode(int joinCode) {
        if(joinCode == this.joinCode){
            return 1;
        } else {
            return 0;
        }
    }
}
