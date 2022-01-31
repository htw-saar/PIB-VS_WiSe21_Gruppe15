package com.htwsaar.client.UserInterface.Menu;

import com.htwsaar.client.UserInterface.TicTacToe.GameLogic;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static final int SIEL_ERSTELLEN = 1;
    private static final int SPIEL_BEITRETEN = 2;
    private static final int BESTENLISTE = 3;
    private static final int ENDE = 0;
    private static int funktion = -1;
    private static final Scanner input = new Scanner(System.in);

    public static void startMenu() {
        while (funktion != ENDE) {
            try {
                funktion = einlesenFunktion();
                ausfuehrenFunktion(funktion);
            } catch (IllegalArgumentException e) {
                e.printStackTrace(System.out);
                System.out.println(e);
            } catch (InputMismatchException e) {
                e.printStackTrace(System.out);
                System.out.println(e);
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Es wurde eine Ausnahme eingefangen und zwar: " + e);
                e.printStackTrace(System.out);
            }
        }
    }

    private static int einlesenFunktion() {
        System.out.print(
                SIEL_ERSTELLEN      + ": Spiel erstellen\n" +
                SPIEL_BEITRETEN     + ": Spiel beitreten\n" +
                BESTENLISTE         + ": Bestenliste anzeigen\n" +
                ENDE                + ": Beenden\n"
        );

        return intEinlesen("\nAuswahl eingeben: ");
    }

    private static void ausfuehrenFunktion(int funktion) {
        if (funktion == SIEL_ERSTELLEN) {
            System.out.println("Spiel erstellen");
            GameLogic.startGame();
        } else if (funktion == SPIEL_BEITRETEN) {
            System.out.println("Spiel beitreten");
        }  else if (funktion == BESTENLISTE) {
            System.out.println("Bestenliste");
        } else if (funktion == ENDE) {
            System.out.println("Das Programm wird beendet.");
        } else {
            System.out.println("Fehlerhafte Auswahl einer Funktion!");
        }

        System.out.println("---------------------------------------");
    }

    /**
     * Eine Methode um Int-Werte einzulesen
     *
     * @return int-wert(um damit in der Fachklasse zu rechnen)
     */
    private static int intEinlesen(String txt) {
        System.out.println(txt);
        return input.nextInt();
    }

}
