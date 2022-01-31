package com.htwsaar.client.UserInterface.Menu;

import com.htwsaar.client.UserInterface.TicTacToe.GameLogic;
import com.htwsaar.client.RMI.Client_RMI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private final int SIEL_ERSTELLEN = 1;
    private final int SPIEL_BEITRETEN = 2;
    private final int BESTENLISTE = 3;
    private final int ENDE = 0;
    private int funktion = -1;
    private final Client_RMI client_rmi;
    private final Scanner input = new Scanner(System.in);

    public Menu(Client_RMI client_rmi) {
        this.client_rmi = client_rmi;
    }

    public void startMenu() {
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

    private int einlesenFunktion() {
        String format = " %2s %6s %2s %22s %2s";
        String spacer = "  +---------+-------------------------+";
        System.out.println(spacer);
        System.out.println(String.format(format, "|", "Nummer", "|", "Funktion", "|"));
        System.out.println(spacer);
        System.out.println(String.format(format, "|", SIEL_ERSTELLEN, "|", "Spiel erstellen", "|"));
        System.out.println(String.format(format, "|", SPIEL_BEITRETEN, "|", "Spiel beitreten", "|"));
        System.out.println(String.format(format, "|", BESTENLISTE, "|", "Bestenliste anzeigen", "|"));
        System.out.println(String.format(format, "|", ENDE, "|", "Beenden", "|"));
        System.out.println(spacer);

        return intEinlesen("\nAuswahl eingeben: ");
    }

    private void ausfuehrenFunktion(int funktion) {
        if (funktion == SIEL_ERSTELLEN) {
            System.out.println("Spiel erstellen:");
            GameLogic.startGame();
        } else if (funktion == SPIEL_BEITRETEN) {
            System.out.println("Spiel beitreten:");
        } else if (funktion == BESTENLISTE) {
            System.out.println("Bestenliste:");
            client_rmi.ShowScoreBoardAll();
        } else if (funktion == ENDE) {
            System.out.println("Das Programm wird beendet.");
        } else {
            System.out.println("Fehlerhafte Auswahl einer Funktion!");
        }
        System.out.println("\n\n\n\n");
    }

    /**
     * Eine Methode um Int-Werte einzulesen
     *
     * @return int-wert(um damit in der Fachklasse zu rechnen)
     */
    private int intEinlesen(String txt) {
        System.out.println(txt);
        return input.nextInt();
    }

}
