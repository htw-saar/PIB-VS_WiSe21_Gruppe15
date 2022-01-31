package com.htwsaar.client.UserInterface.Menu;

import com.htwsaar.client.Client;
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
        System.out.print(
                SIEL_ERSTELLEN + ": Spiel erstellen\n" +
                        SPIEL_BEITRETEN + ": Spiel beitreten\n" +
                        BESTENLISTE + ": Bestenliste anzeigen\n" +
                        ENDE + ": Beenden\n"
        );

        return intEinlesen("\nAuswahl eingeben: ");
    }

    private void ausfuehrenFunktion(int funktion) {
        if (funktion == SIEL_ERSTELLEN) {
            System.out.println("Spiel erstellen");
        } else if (funktion == SPIEL_BEITRETEN) {
            System.out.println("Spiel beitreten");
        } else if (funktion == BESTENLISTE) {
            System.out.println("Bestenliste");
            client_rmi.ShowScoreBoardAll();
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
    private int intEinlesen(String txt) {
        System.out.println(txt);
        return input.nextInt();
    }

}
