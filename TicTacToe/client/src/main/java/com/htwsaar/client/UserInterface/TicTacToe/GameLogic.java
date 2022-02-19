package com.htwsaar.client.UserInterface.TicTacToe;

import com.htwsaar.client.RMI.Client_RMI;

import java.util.*;

public class GameLogic {
    //Initialisierung (Zurücksetzung) des Spielbretts.
    static ArrayList<Integer> player1Positions = new ArrayList<Integer>();
    static ArrayList<Integer> player2Positions = new ArrayList<Integer>();
    static final Scanner input = new Scanner(System.in);

    /**
     * Eine Methode für die locale Ausführung des Spiels WIP/umstellung auf Onlinefunktionalität
     */
    public static void startGame(Client_RMI client_rmi, String username) {
        char[][] gameBoard = initGameboard();
        // Game Board ins Terminal Printen:
        printGameBoard(gameBoard);
        client_rmi.createGame(client_rmi.getLoggedInUser());
        while(client_rmi.checkGameStart(username) == false){
            System.out.println("Server waits for Player 2");;
        }
        while (true) {
            //Player1:
            String winBreak = winnerChecker();
            if (winBreak == "") {
                player1Logic(gameBoard);
            } else if (winBreak != "") {
                System.out.println(winnerChecker());
                printGameBoard(gameBoard);
                break;
            }
            printGameBoard(gameBoard);

            //Player2:
            String winBreak2 = winnerChecker();
            if (winBreak2 == "") {
                player2Logic(gameBoard);
            } else if (winBreak2 != "") {
                System.out.println(winnerChecker());
                printGameBoard(gameBoard);
                break;
            }
            printGameBoard(gameBoard);
        }
    }

    /**
     * Eine Methode für die Spieler 2 Logik
     *
     * @param gameBoard, Spielbrett
     */
    //Umschreiben der übergabeparameter für Onlinefunktionalität wegen Input
    // Player1:
    public static void player1Logic(char[][] gameBoard) {
        System.out.println("Player1: Trage eine Ziffer ein zwischen 1 und 9:");
        int player1Pos = intEinlesen();
        while (player1Positions.contains(player1Pos) || player2Positions.contains(player1Pos)) {
            System.out.println("Player1: Position bereits besetzt, wähle eine andere Position:");
            player1Pos = intEinlesen();
        }
        System.out.println(player1Pos);
        placeSymb(gameBoard, "player1", player1Pos);
    }

    /**
     * Eine Methode für die Spieler 2 Logik
     *
     * @param gameBoard, Spielbrett
     */
    //Umschreiben der übergabeparameter für Onlinefunktionalität wegen Input
    // Player2:
    public static void player2Logic(char[][] gameBoard) {
        System.out.println("Player2: Trage eine Ziffern ein zwischen 1 und 9:");
        int player2Pos = intEinlesen();
        while (player1Positions.contains(player2Pos) || player2Positions.contains(player2Pos)) {
            System.out.println("Player2: Position bereits besetzt, wähle eine andere Position:");
            player2Pos = intEinlesen();
        }
        System.out.println(player2Pos);
        placeSymb(gameBoard, "player2", player2Pos);
    }

    /**
     * Eine Methode zum ausgeben des Spielbretts
     *
     * @param gameBoard, Spielbrett
     */
    public static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) { // for each Schleife für jeweilige Reihe in GameBoard
            for (char c : row) { // for each Character an der jeweiligen Stelle
                System.out.print(c);
            }
            System.out.println();
        }
    }

    /**
     * Eine Methode zum setzen von Symbolen auf dem Spielbrett
     *
     * @param gameBoard, Spielbrett
     * @param user,      Spielerbezeichnung (player1 oder player2)
     * @param pos,       vom Spieler gesetzte Position
     */
    public static void placeSymb(char[][] gameBoard, String user, int pos) {
        char symbol = 'U';

        // Spielerunterscheidung ob ein X oder ein O gesetzt wird
        if (user.equals("player1")) {
            symbol = 'X';
            if (!(player1Positions.contains(pos)) && !(player2Positions.contains(pos))) {
                player1Positions.add(pos);
            }
        } else if (user.equals("player2")) {
            symbol = 'O';
            if (!(player1Positions.contains(pos)) && !(player2Positions.contains(pos))) {
                player2Positions.add(pos);
            }
        }

        /** Switch Case Block der an der jeweiligen Stelle die mitgegeben
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

    /**
     * Eine Methode welche die Siegeskonditionen überprüft
     *
     * @return String-wert(Gibt Ergebnis zurück)
     */
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

        for (List l : winningCondition) {
            if (player1Positions.containsAll(l)) {
                return "\n \nPlayer1 gewinnt!";
            } else if (player2Positions.containsAll(l)) {
                return "\n \nPlayer2 gewinnt!";
            }
        }
        if (player1Positions.size() + player2Positions.size() == 9) {
            for (List l : winningCondition) {
                if (player1Positions.containsAll(l)) {
                    return "\n \nPlayer1 gewinnt!";
                } else if (player2Positions.containsAll(l)) {
                    return "\n \nPlayer2 gewinnt!";
                }
            }
            return "\n \nBord ist voll, keiner gewinnt!";
        }
        return "";
    }


    /**
     * Eine Methode um Int-Werte einzulesen
     *
     * @return int-wert(um damit in der Fachklasse zu rechnen)
     */
    private static int intEinlesen() {
        try {
            int zahl = input.nextInt();

            while (zahl < 1 || zahl > 9) {
                System.out.println("bitte neu eingeben!");
                zahl = input.nextInt();

            }

            return zahl;

        } catch (InputMismatchException e) {
            input.next();
            System.out.println("Bitte eine Zahl eingeben!");
            return intEinlesen();
        }

    }

    /**
     * Eine Methode um das Spielbrett und gegsetzt Werte für ein neues Spiel
     * zu initialisieren
     *
     * @return Spielbrett
     */
    public static char[][] initGameboard() {
        player1Positions = new ArrayList<Integer>();
        player2Positions = new ArrayList<Integer>();

        //Gameboard Array 3 Reihen 3 Spalten, 3x3 Matrix
        char[][] gameBoard = {
                {'1', '|', '2', '|', '3'},
                {'-', '+', '-', '+', '-'},
                {'4', '|', '5', '|', '6'},
                {'-', '+', '-', '+', '-'},
                {'7', '|', '8', '|', '9'}
        };
        return gameBoard;
    }

    public static void joinGame(int GameID, Client_RMI client_rmi, String username) {
        if(client_rmi.joinGame(GameID,username)){
            System.out.println("Spiel erfolgreich beigetreten!");
        }
        else{
            System.out.println("Spiel nicht erfolgreich beigetreten!");
        }
    }
}

