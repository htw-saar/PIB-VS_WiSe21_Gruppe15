package com.htwsaar.server.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TicTacToe {

    public enum Winner {
        Player1("X"),
        Player2("O"),
        NONE("none"),
        UNSETTELD("unsetteld");

        public final String label;

        private Winner(String label) {
            this.label = label;
        }
    }

    private String[] gameboard = new String[9];
    private final int[][] winConditions;

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
    }

    private void setField(Winner player, int pos){
        gameboard[pos] = player.label;
    }

    private String[] initGameboard(){
        for (int i = 0; i < gameboard.length; i++) {
            int field = i + 1;
            this.gameboard[i] = field + "";
        }
        return this.gameboard;
    }



    public Winner checkWinCondition(Winner player, int pos){
        for (int[] winCondition : winConditions){
            if (Objects.equals(gameboard[winCondition[0]], gameboard[winCondition[1]])){
                if (Objects.equals(gameboard[winCondition[1]], gameboard[winCondition[2]])){
                    return player;
                }
            }
        }

        return checkGameboardFull();
    }

    private Winner checkGameboardFull(){
        for (int i = 0; i < gameboard.length; i++) {
            int field = i+1;
            if (gameboard[i].equals(field + "")){
                return Winner.NONE;
            }
        }
        return Winner.UNSETTELD;
    }
}
