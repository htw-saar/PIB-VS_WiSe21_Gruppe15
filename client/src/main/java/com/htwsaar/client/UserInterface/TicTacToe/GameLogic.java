package com.htwsaar.client.UserInterface.TicTacToe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameLogic {

    static ArrayList<Integer> player1Positions = new ArrayList<Integer>();
    static ArrayList<Integer> player2Positions = new ArrayList<Integer>();

    public static void startGame() {
        player1Positions = new ArrayList<Integer>();
        player2Positions = new ArrayList<Integer>();

        //Gameboard Array 3 Reihen 3 Spalten, 3x3 Matrix
        char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}
        };
        // Game Board ins Terminal Printen:
        printGameBoard(gameBoard);

        while(true) {
            player1Logic(gameBoard);
            String winBreak = winnerChecker();
            if (winBreak != ""){
                System.out.println(winnerChecker());
                break;
            }
            printGameBoard(gameBoard);

            player2Logic(gameBoard);
            String winBreak2 = winnerChecker();
            if (winBreak2 != ""){
                System.out.println(winnerChecker());
            }
            printGameBoard(gameBoard);
        }
    }

    public static void player1Logic(char[][] gameBoard) {
        Scanner player1Input = new Scanner(System.in);
        // Player1:
        System.out.println("Player1: Trage eine Ziffer ein zwischen 1 und 9:");
        int player1Pos = player1Input.nextInt();
        while(player1Positions.contains(player1Pos) || player2Positions.contains(player1Pos)) {
            System.out.println("Player1: Position bereits besetzt, wähle eine andere Position:");
            player1Pos = player1Input.nextInt();
        }
        System.out.println(player1Pos);
        placeSymb(gameBoard, "player1", player1Pos);
        printGameBoard(gameBoard);
    }

    public static void player2Logic(char[][] gameBoard) {
        Scanner player2Input = new Scanner(System.in);
        //Player2:
        System.out.println("Player2: Trage eine Ziffern ein zwischen 1 und 9:");
        int player2Pos = player2Input.nextInt();
        while(player1Positions.contains(player2Pos) || player2Positions.contains(player2Pos)) {
            System.out.println("Player2: Position bereits besetzt, wähle eine andere Position:");
            player2Pos = player2Input.nextInt();
        }
        System.out.println(player2Pos);
        placeSymb(gameBoard, "player2", player2Pos);
        printGameBoard(gameBoard);
    }

    public static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) { // for each Schleife für jeweilige Reihe in GameBoard
            for (char c : row) { // for each Character an der jeweiligen Stelle
                System.out.print(c);
            }
            System.out.println();
        }
    }

    // Methode zum Zeichen setzen, kriegt
    // GameBoard User und Position mit
    public static void placeSymb(char[][] gameBoard, String user, int pos) {
        char symbol = 'U';

        // Spielerunterscheidung ob ein X oder ein O gesetzt wird
        if (user.equals("player1")) {
            symbol = 'X';
            if (!(player1Positions.contains(pos)) && !(player2Positions.contains(pos))){
                player1Positions.add(pos);
            }
        }
        else if (user.equals("player2")) {
            symbol = 'O';
            if (!(player1Positions.contains(pos)) && !(player2Positions.contains(pos))){
                player2Positions.add(pos);
            }
        }

        /* Switch Case Block der an der jeweiligen Stelle die mitgegeben
           wird das Symbol des jeweiligen Spielers einträgt*/
        switch (pos) {
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

    public static String winnerChecker() {
        List upperRow = Arrays.asList(1, 2, 3);
        List middleRow = Arrays.asList(4, 5, 6);
        List bottomRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List diagonal1 = Arrays.asList(1, 5, 9);
        List diagonal2 = Arrays.asList(7, 5, 3);

        List<List> winningCondition = new ArrayList<List>();
        winningCondition.add(upperRow);
        winningCondition.add(middleRow);
        winningCondition.add(bottomRow);
        winningCondition.add(leftCol);
        winningCondition.add(midCol);
        winningCondition.add(rightCol);
        winningCondition.add(diagonal1);
        winningCondition.add(diagonal2);

        for(List l : winningCondition) {
            if(player1Positions.containsAll(l)){
                return "Player1 gewinnt!";
            }
            else if(player2Positions.containsAll(l)){
                return "Player2 gewinnt!";
            }
            else if(player1Positions.size() + player2Positions.size() == 9) {
                if(!(player1Positions.containsAll(l) || !(player2Positions.containsAll(l)))) {
                    return "Player1&2 gewinnt!";

                }
                return "Bord ist voll, keiner gewinnt!";
            }
        }
        return "";
    }
}


//KNOWN ISSUES:
/*
- Wenn bei vollem Bord einer durch setzten des letzten Symbols gewinnt, wird trotzdem unentschieden ausgegeben.







 */
