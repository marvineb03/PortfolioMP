package Akteure;

import Handel.*;
import Mains.*;

import java.util.Random;


public class Konsument extends Akteur {

    /**
     *
     * @param anzeigeName Name des Produzenten
     *
     * Konstruktor der Klasse Konsument.
     * Übergabe der Konstruktor-Parameter an Klassenattribute.
     */
    public Konsument(String anzeigeName) {
        super( anzeigeName);
    }

    /**
     * Run Methode der Klasse Thread.
     * Methode wird pro Zeiteinheit einmal ausgeführt.
     */
    @Override
    public void run() {
        //Zufällige Zahl von 3 - 5
        int anzahlErwerbe = 3 + new Random().nextInt(3);

        //For-Schleife, welche anzahlErwerbe-Mal durchläuft.
        for (int i = 0; i < anzahlErwerbe; i++) {
            //Zufälliges Angebot von einem Kosumgut durch Marktplatz.
            KonsumgutAngebot angebot = Marktplatz.getInstance().getRandomAngebot();

            //Prüfung, ob Angebot null ist. Falls ja, existieren derzeit keine Angebote und die Schleife wird abgebrochen.
            if(angebot == null) {
                break;
            }

            //Gültiges Angebot wird erworben.
            Marktplatz.getInstance().transaktion(this, angebot);
        }
    }
}