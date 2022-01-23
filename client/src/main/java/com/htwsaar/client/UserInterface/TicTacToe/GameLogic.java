package com.htwsaar.client.UserInterface.TicTacToe;

import java.util.Scanner;

public class GameLogic {
      public static void main(String[] args) {


        char[][] gameBoard = {{' ', '|', ' ', '|', ' '},            //Gameboard Array 3 Reihen 3 Spalten, 3x3 Matrix
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}
        };
        printGameBoard(gameBoard);                                  // Game Board ins Terminal Printen

          Scanner scan = new Scanner(System.in);                    // Scanner Anlegen
          System.out.println("Trage Ziffern ein zwischen 1 und 9:");
          int pos = scan.nextInt();                                 // Kästschen auswählen
          System.out.println(pos);

          placeSymb(gameBoard,"player1",pos);

          printGameBoard(gameBoard);







      }
    public static void printGameBoard(char [] [] gameBoard ){
        for (char[] row : gameBoard) { // for each Schleife für jeweilige Reihe in GameBoard
            for (char c : row) { // for each Character an der jeweiligen Stelle
                System.out.print(c);

            }
            System.out.println();

        }

    }
    public static void placeSymb(char[][] gameBoard, String user, int pos){  // Methode zum Zeichen setzen, kriegt GameBoard User und Position mit

          char symbol = ' ';

          if(user.equals("player1")) {  // Spielerunterscheidung ob ein X oder ein O gesetzt wird
              symbol = 'X';

          }else if(user.equals("player2")) {
                  symbol = 'O';
              }


        switch (pos) {  // Switch Case Block der an der jeweiligen Stelle die mitgegeben wird das Symbol des jeweiligen Spielers einträgt
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                System.out.println("Bitte Zahl zwischen 1 und 9 eingeben!");
                break;
        }
    }

}
