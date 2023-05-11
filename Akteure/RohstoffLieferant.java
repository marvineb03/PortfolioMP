package Akteure;

import Handel.*;
import Mains.*;

import java.util.ArrayList;
import java.util.Random;

public class RohstoffLieferant extends Akteur {
    private final int minVolumen; //Minimale zu produzierende Rohstoffmenge
    private final int maxVolumen; //Maximale zu produzierende Rohstoffmenge

    /**
     *
     * @param anzeigeName Name des Lieferanten
     * @param minVolumen Integer minimale Rohstoffmenge
     * @param maxVolumen Integer maximale Rohstoffmenge
     *
     * Konstruktor der Klasse Rohstofflieferant.
     * Übergabe der Konstruktor-Parameter an Klassenattribute.
     */
    public RohstoffLieferant(String anzeigeName, int minVolumen, int maxVolumen) {
        super( anzeigeName);
        this.minVolumen = minVolumen;
        this.maxVolumen = maxVolumen;
    }

    /**
     * Run Methode der Klasse Thread.
     * Methode wird pro Zeiteinheit einmal ausgeführt.
     */
    @Override
    public void run() {
        //Zufällige Anzahl an Rohstoffen, welche in der jeweiligen Zeiteinheit produziert werden sollen.
        int anzahlRohstoffe = 2 + new Random().nextInt(4);

        //ArrayListe mit den zu produzierenden Rohstoffen.
        ArrayList<Rohstoff> rohstoffe = new ArrayList<>();

        //Zähl-Schleife, welche anzahlRohstoffe-Mal durchläuft.
        for (int i = 0; i < anzahlRohstoffe; i++) {
            //Hinzufügen eines zufälligen Rohstoffs in ArrayList rohstoffe.
            rohstoffe.add(getRandomRohstoff(rohstoffe));
        }

        //Produzieren der Rohstoffe der ArrayList rohstoffe.
        produzieren(rohstoffe);

        //Ausgabe des aktuellen Bestandes.
        Logger.printBestand(this);

        //Beantwortung von Rohstoff-Anfragen am Marktplatz.
        anfrageBeantworten();
    }

    /**
     * Methode, welche nach Anfragen am Markplatz sucht, um diese zu beantworten.
     */
    public synchronized void anfrageBeantworten() {
        //For-Schleife, welche das Set<VerbrauchsGut> (Keys) der Bestands-Hashmap durchläuft (Rohstoffe).
        for(int i = 0 ; i < super.bestand.size(); i++){
            VerbrauchsGut rohstoff = super.bestand.get(i);
            //Prüfung ob der Bestand des Verbrauchguts 0 ist.
            if(super.getBestand().isEmpty()) {
                continue; 
            }

           //ArrayList, welche alle Rohstoff-Anfragen des derzeitigen Rohstoffs (rohstoff) beinhalten.
            ArrayList<RohstoffAnfrage> verbrauchsGutAnfragen = Marktplatz.getInstance().getVGAnfragen(rohstoff);
            //For-Schleife, welche die ArrayList<RohstoffAnfrage> verbrauchsGutAnfragen durchläuft.
            for(RohstoffAnfrage anfrage : verbrauchsGutAnfragen) {
                //Aktueller Bestand des Rohstoffs rohstoff.
                int vGBestand = super.getVGBestand(rohstoff);
                //Volumen der Anfrage.
                int anfrageVolumen = anfrage.getVolumen();

                //Prüfung, ob Bestand für eine Lieferung ausreicht.
                if(vGBestand < anfrageVolumen) {
                    continue;
                }

                //Erstellung eines RohstoffLieferung Objektes, mit dem jeweiligen Rohstoff und Volumen.
                RohstoffLieferung lieferung = new RohstoffLieferung(this, rohstoff, anfrageVolumen);
                //Übergabe zur Transaktion und Lieferung an Marktplatz.
                Marktplatz.getInstance().transaktion(anfrage, lieferung);

                //Prüfung, ob Bestand des Rohstoffs 0 ist und somit nicht weiter für Anfragen verwendet werden kann.
                if(super.getBestand().size() == 0) {
                    break;
                }
            }
        }
    }

    /**
     *
     * @param rohstoffe ArrayList<Rohstoff> für Produktion
     *
     * Produktion der übergebenen Rohstoffe.
     */
    private synchronized void produzieren(ArrayList<Rohstoff> rohstoffe) {
        //For-Schleife, welche die ArrayList<Rohstoff> rohstoffe durchläuft.
        for(Rohstoff rohstoff : rohstoffe) {
            //Zufälliges Produktionsvolumen von 5-10
            int volumen = minVolumen + new Random().nextInt((maxVolumen - minVolumen) + 1);
            //Erhöhung des Bestandes
            super.addBestand(rohstoff, volumen);
        }
    }

    /**
     *
     * @param blocked ArrayList<Rohstoff> blockierter Rohstoffe
     * @return Rohstoff zufällig bestimmt
     *
     * Bestimmung eines zufälligen Rohstoffs mit anschließender Prüfung auf Duplikate
     */
    private Rohstoff getRandomRohstoff(ArrayList<Rohstoff> blocked) {
        //Array mit allen Values des enum Rohstoff.
        Rohstoff[] rohstoffe = Rohstoff.values();
        Rohstoff rohstoff = null;
        //Prüfung, ob Rohstoff rohstoff null ist oder in der ArrayList<Rohstoff> blocked enthalten ist.
        while (rohstoff == null || blocked.contains(rohstoff)) {
            //Zufällige Index-Zahl
            int random = new Random().nextInt(rohstoffe.length);
            rohstoff = rohstoffe[random];
        }
        //Ein zufälliger Rohstoff würd übergeben, welcher nicht in ArrayList<Rohstoff> blocked enthalten ist.
        return rohstoff;
    }
}
