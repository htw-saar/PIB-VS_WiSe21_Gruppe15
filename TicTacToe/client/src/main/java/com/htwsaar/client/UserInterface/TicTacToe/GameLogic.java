package com.htwsaar.client.UserInterface.TicTacToe;

import com.htwsaar.client.Client;
import com.htwsaar.client.RMI.Client_RMI;
import com.htwsaar.server.Game.TicTacToe;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameLogic {
    //Initialisierung (Zurücksetzung) des Spielbretts.
    private ArrayList<Integer> player1Positions = new ArrayList<Integer>();
    private ArrayList<Integer> player2Positions = new ArrayList<Integer>();
    private final Scanner input = new Scanner(System.in);
    private String[][] gameBoard;
    private Client_RMI client_rmi;
    private String username;


    /**
     * Eine Methode für die locale Ausführung des Spiels WIP/umstellung auf Onlinefunktionalität
     */
    public void startGame(Client_RMI client_rmi, String username) {
        this.client_rmi = client_rmi;
        this.username = username;
        gameBoard = initGameboard();
        // Game Board ins Terminal Printen:
//        printGameBoard(gameBoard);
        client_rmi.createGame(client_rmi.getLoggedInUser());
        while (!client_rmi.checkGameStart(username)) {
            System.out.println("Server waits for Player 2");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        playGame();
    }

    private void playGame() {
        TicTacToe.Winner winBreak = TicTacToe.Winner.NONE;
        String[] serverGameboard;
        while (winBreak == TicTacToe.Winner.NONE) {

            waitOnPlayer();
            serverGameboard = client_rmi.returnGameboard(username);
            placeSymb(serverGameboard);
            printGameBoard(gameBoard);
            System.out.println("Setze Feld 1 bis 9");
            int field = intEinlesen();
            System.out.println();
            //Player:
            //TODO: Überprüfung ob Felder bereits gesetzt sind
            //TODO: Wincondition nach return von gameboard
            winBreak = client_rmi.setField(username, field - 1);
            while (winBreak.equals(TicTacToe.Winner.FIELDSET)) {
                System.out.println("Das Feld ist bereits gesetzt, bitte anderes Feld wählen: ");
                field = intEinlesen();
                winBreak = client_rmi.setField(username, field - 1);
            }
            System.out.println("TEEEEEEEEEEEEEEEEEEEEEST");
            serverGameboard = client_rmi.returnGameboard(username);
            placeSymb(serverGameboard);
            printGameBoard(gameBoard);
            if (!winBreak.label.equals("none")){
                System.out.println("Der Sieger ist: \"" + username + "\" mit dem Symbol: " + winBreak.label);
                break;
            }
        }
    }


    public static String[] onlineBoard(String username) {
        Client_RMI client_rmi = new Client_RMI();
        String[] gameboard;
        gameboard = client_rmi.returnGameboard(username);
        return gameboard;
    }

    private void waitOnPlayer() {
        while (!client_rmi.getActivePlayer(username).equals(username)) {
            System.out.println("Warte auf anderen Spieler"); // ab dieser Zeile müsste die Game Logic für Spieler 2 definiert werden
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Eine Methode für die Spieler Logik
     */
    //Umschreiben der übergabeparameter für Onlinefunktionalität wegen Input
    // Player1:
    public void playerLogic() {
        System.out.println("Player1: Trage eine Ziffer ein zwischen 1 und 9:");
        int player1Pos = intEinlesen();
        while (player1Positions.contains(player1Pos) || player2Positions.contains(player1Pos)) {
            System.out.println("Player1: Position bereits besetzt, wähle eine andere Position:");
            player1Pos = intEinlesen();
        }
        System.out.println(player1Pos);
        //placeSymb(gameBoard);
    }

    /**
     * Eine Methode für die Spieler 2 Logik
     *
     * @param gameBoard, Spielbrett
     */
    //Umschreiben der übergabeparameter für Onlinefunktionalität wegen Input
    // Player2:
    public void player2Logic(char[][] gameBoard) {
        System.out.println("Player2: Trage eine Ziffern ein zwischen 1 und 9:");
        int player2Pos = intEinlesen();
        while (player1Positions.contains(player2Pos) || player2Positions.contains(player2Pos)) {
            System.out.println("Player2: Position bereits besetzt, wähle eine andere Position:");
            player2Pos = intEinlesen();
        }
        System.out.println(player2Pos);
//        placeSymb(gameBoard, "player2", player2Pos);
    }

    /**
     * Eine Methode zum ausgeben des Spielbretts
     *
     * @param gameBoard, Spielbrett
     */
    public static void printGameBoard(String[][] gameBoard) {
        for (String[] row : gameBoard) { // for each Schleife für jeweilige Reihe in GameBoard
            for (String c : row) { // for each Character an der jeweiligen Stelle
                System.out.print(c);
            }
            System.out.println();
        }
    }

    /**
     * Eine Methode zum setzen von Symbolen auf dem Spielbrett
     * <p>
     * //     * @param gameBoard, Spielbrett
     * //     * @param user,      Spielerbezeichnung (player1 oder player2)
     */
    public void placeSymb(String[] server_gameboard) {
        for (int i = 0; i < server_gameboard.length; i++) {
            switch (i + 1) {
                case 1:
                    gameBoard[0][0] = server_gameboard[i];
                    break;
                case 2:
                    gameBoard[0][2] = server_gameboard[i];
                    break;
                case 3:
                    gameBoard[0][4] = server_gameboard[i];
                    break;
                case 4:
                    gameBoard[2][0] = server_gameboard[i];
                    break;
                case 5:
                    gameBoard[2][2] = server_gameboard[i];
                    break;
                case 6:
                    gameBoard[2][4] = server_gameboard[i];
                    break;
                case 7:
                    gameBoard[4][0] = server_gameboard[i];
                    break;
                case 8:
                    gameBoard[4][2] = server_gameboard[i];
                    break;
                case 9:
                    gameBoard[4][4] = server_gameboard[i];
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Eine Methode welche die Siegeskonditionen überprüft
     *
     * @return String-wert(Gibt Ergebnis zurück)
     */
    public String winnerChecker() {
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
    private int intEinlesen() {
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
    public String[][] initGameboard() {
        player1Positions = new ArrayList<Integer>();
        player2Positions = new ArrayList<Integer>();

        //Gameboard Array 3 Reihen 3 Spalten, 3x3 Matrix
        String[][] gameBoard = {
                {"1", "|", "2", "|", "3"},
                {"-", "+", "-", "+", "-"},
                {"4", "|", "5", "|", "6"},
                {"-", "+", "-", "+", "-"},
                {"7", "|", "8", "|", "9"}
        };
        return gameBoard;
    }

    public void joinGame(int GameID, Client_RMI client_rmi, String username) {
        if (client_rmi.joinGame(GameID, username)) {
            System.out.println("Spiel erfolgreich beigetreten!");
            this.client_rmi = client_rmi;
            this.username = username;
            gameBoard = initGameboard();
            playGame();
        } else {
            System.out.println("Spiel nicht erfolgreich beigetreten!");
        }
    }
}

