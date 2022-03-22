package com.htwsaar.client.UserInterface.TicTacToe;

import com.htwsaar.client.RMI.Client_RMI;
import com.htwsaar.server.Game.TicTacToe;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameLogic {
    private final Scanner input = new Scanner(System.in);
    private final Client_RMI client_rmi;
    private final String username;
    private final String waitingList = "Wait";
    private final String rematchList = "Rematch";

    private String presharedKey;

    /**
     * Konstruktor der GameLogic Klasse
     *
     * @param client_rmi uebergebenes client_rmi object
     * @param username name des users
     */
    public GameLogic(Client_RMI client_rmi, String username) {
        this.client_rmi = client_rmi;
        this.username = username;
    }


    /**
     * Methode um Spiel zu erstellen und Spiel zu Spielen für den ersten Spieler
     */
    public void createGame() {
        presharedKey = client_rmi.createGame(client_rmi.getLoggedInUser());
        System.out.println("... Warte auf Spieler 2 ...");
        while (!client_rmi.checkGameStart(username, waitingList)) {
            waitForInteraction();
        }
        playGame();
    }

    /**
     * Methode um Spiel beizutreten für Spieler 2, spielt auch über diese Methode
     *
     * @param GameID Spielecode der zum beitreten benoetigt wird
     */
    public void joinGame(int GameID) {
        presharedKey = client_rmi.joinGame(GameID, username);
        if (presharedKey != null) {
            System.out.println("Spiel erfolgreich beigetreten!");
            playGame();
        } else {
            System.out.println("Spiel nicht erfolgreich beigetreten!");
        }
    }

    /**
     * Methode für ein Rematch zwischen den Spielern
     */

    public void rematchGame() {
        client_rmi.rematchGame(username);
        System.out.println("... Warte auf Spieler 2 ...");
        while (!client_rmi.checkGameStart(username, rematchList)) {
            waitForInteraction();
        }
        playGame();
    }

    /**
     * Implementierung playGame Methode, überprüft Win Conditions und spielt dementsprechend weiter
     */
    private void playGame() {
        TicTacToe.Winner winBreak = TicTacToe.Winner.NONE;
        String[] serverGameboard;
        while (winBreak == TicTacToe.Winner.NONE) {
            winBreak = waitOnPlayer();
            if (winBreak.equals(TicTacToe.Winner.Player1) ||
                    winBreak.equals(TicTacToe.Winner.Player2) ||
                    winBreak.equals(TicTacToe.Winner.UNSETTELD)) {
                break;
            }
            serverGameboard = client_rmi.returnGameboard(username);
            printGameBoard(serverGameboard);
            System.out.println("Setze Feld 1 bis 9: ");
            int field = intEinlesen();
            winBreak = setField(field);
            serverGameboard = client_rmi.returnGameboard(username);
            printGameBoard(serverGameboard);
        }
        if (winBreak != TicTacToe.Winner.UNSETTELD) {
            System.out.println("Spieler " + winBreak.label + " hat gewonnen.");
        } else {
            System.out.println("Das Spiel ist Unentschieden.");
        }

    }

    /**
     * Methode um ein Feld auf dem Game Board zu setzen und der Überprüfung ob ein Feld bereits gesetzt ist
     *
     * @param field Feld das gesetzt werden soll
     * @return Winner Enum
     */
    private TicTacToe.Winner setField(int field) {
        TicTacToe.Winner winBreak;
        winBreak = client_rmi.setField(username, field - 1, presharedKey);
        while (winBreak.equals(TicTacToe.Winner.FIELDSET)) {
            System.out.println("Das Feld ist bereits gesetzt, bitte anderes Feld wählen: ");
            field = intEinlesen();
            winBreak = client_rmi.setField(username, field - 1, presharedKey);
        }
        return winBreak;
    }

    /**
     * Methode um auf Spielerzug zu warten
     *
     * @return Winner Enum
     */
    private TicTacToe.Winner waitOnPlayer() {
        System.out.println("Warte auf anderen Spieler");
        while (!client_rmi.getActivePlayer(username).equals(username)) {
            waitForInteraction();
        }

        return checkWinner();
    }

    /**
     * Methode zu Überprüfung des Gewinners
     *
     * @return Winner Enum
     */
    private TicTacToe.Winner checkWinner() {
        return client_rmi.getGameStatus(username);
    }

    /**
     * Eine Methode zum ausgeben des Spielbretts
     *
     * @param gameBoard, Spielbrett
     */
    public static void printGameBoard(String[] gameBoard) {
        String format = " %2s %3s %2s %3s %2s %3s %2s";
        String spacer = "  +------+------+------+";
        System.out.println(spacer);
        for (int i = 0; i < gameBoard.length; i += 3) {
            System.out.printf((format) + "%n", "|", gameBoard[i], "|", gameBoard[i + 1], "|", gameBoard[i + 2], "|");
            System.out.println(spacer);
        }
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
     * Sleep Methode um auf Interaktion zu warten
     */
    private void waitForInteraction() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getPresharedKey() {
        return presharedKey;
    }
}

