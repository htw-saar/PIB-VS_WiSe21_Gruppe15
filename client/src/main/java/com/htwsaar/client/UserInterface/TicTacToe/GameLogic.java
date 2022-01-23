package com.htwsaar.client.UserInterface.TicTacToe;

public class GameLogic {
      public static void main(String[] args) {


        char[][] gameBoard = {{' ', '|', ' ', '|', ' '},            //Gameboard Array 3 Reihen 3 Spalten, 3x3 Matrix
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}
        };
        printGameBoard(gameBoard);// Game Board ins Terminal Printen




    }
    public static void printGameBoard(char [] [] gameBoard ){
        for (char[] row : gameBoard) { // for each Schleife für jeweilige Reihe in GameBoard
            for (char c : row) { // for each Character an der jeweiligen Stelle
                System.out.print(c);

            }
            System.out.println();

        }

    }
}
