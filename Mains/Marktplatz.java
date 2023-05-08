package Mains;

import Akteure.Akteur;
import Akteure.VerbrauchsGut;
import Handel.*;

import java.util.ArrayList;
import java.util.Random;

public class Marktplatz {
    private static final Marktplatz instance = new Marktplatz(); //Instanziierung der Singleton-Klasse Marktplatz
    private final ArrayList<RohstoffAnfrage> anfragen; //ArrayList der Rohstoff-Anfragen
    private final ArrayList<KonsumgutAngebot> angebote; //ArrayList der Konsumgut-Angebote

    /**
     * Konstruktor der Klasse Marktplatz.
     */
    private Marktplatz() {
        this.anfragen = new ArrayList<>();
        this.angebote = new ArrayList<>();
    }

    /**
     * Speicher der Rohstoff-Anfragen in der ArrayList anfragen.
     * @param rohstoffAnfrage Neue Rohstoff-Anfrage
     */
    public void stelleAnfrage(RohstoffAnfrage rohstoffAnfrage) {

        this.anfragen.add(rohstoffAnfrage);
    }

    /**
     * Speicher der Konsumgut-Angebote in der ArrayList angebote.
     * @param konsumgutAngebot Neues Konsumgut-Angebot
     */
    public void stelleAngebot(KonsumgutAngebot gutAngebot) {

        this.angebote.add(gutAngebot);
    }

    /**
     * Filtern von Rohstoff-Anfragen anhand des entsprechenden VerbrauchsGut.
     * @param verbrauchsGut Filterkriterium
     * @return ArrayList mit gefilterten Rohstoff-Anfragen nach VerbrauchsGut
     */
    public ArrayList<RohstoffAnfrage> getVGAnfragen(VerbrauchsGut verbrauchsGut) {
        ArrayList<RohstoffAnfrage> matches = new ArrayList<>();

        //For-Schleife, welche alle Anfragen der ArrayList anfragen durchgeht.
        for(RohstoffAnfrage anfrage : this.anfragen) {
            //Prüfung, ob VerbrauchsGut der Anfrage mit dem Parameter verbrauchsGut übereinstimmt.
            if(anfrage.getVerbrauchsGut().equals(verbrauchsGut)) {
                //Bei erfolgreicher Prüfung, wird das Match in die ArrayList matches hinzugefügt.
                matches.add(anfrage);
            }
        }

        return matches;
    }

    /**
     *
     * @param produzent Filterkriterium
     * @return ArrayList mit gefilterten Rohstoff-Anfragen nach Akteur
     */
    public ArrayList<RohstoffAnfrage> getAnfragenByAkteur(Akteur produzent) {
        ArrayList<RohstoffAnfrage> matches = new ArrayList<>();

        //For-Schleife, welche alle Anfragen der ArrayList anfragen durchgeht.
        for(RohstoffAnfrage anfrage : this.anfragen) {
            //Prüfung, ob Akteur der Anfrage mit dem Akteuer von Objekt verbrauchsGut übereinstimmt.
            if(anfrage.getAkteur().equals(produzent)) {
                //Bei erfolgreicher Prüfung, wird das Match in die ArrayList matches hinzugefügt.
                matches.add(anfrage);
            }
        }

        return matches;
    }

    /**
     * Rückgabe eines zufälligen KonsumgutAngebot Objektes aus der ArrayList angebote.
     * @return Zufälliges KonsumgutAngebot
     */
    public KonsumgutAngebot getRandomAngebot() {
        //Prüfung, ob sich Objekte in der ArrayList angebote befindet.
        if(this.angebote.isEmpty()) {
            return null;
        }

        //Zufälliger Index-Wert anhand der Inhaltsgröße der ArrayList
        int index = new Random().nextInt(this.angebote.size());
        return this.angebote.get(index);
    }

    /**
     * Prüfung, ob ein Konsumgut-Angebot bereits abgelaufen ist.
     * @param angebot Zu prüfendes Konsumgut-Angebot
     * @return Gültigkeit des Konsumgut-Angebotes.
     */
    public boolean isAngebotValid(KonsumgutAngebot angebot) {

        return this.angebote.contains(angebot);
    }

    /**
     * Bedienung eines Kaufes eines Konsumgut-Angebotes.
     * @param akteur Käufer Konsumgut-Angebot
     * @param angebot Konsumgut-Angebot
     */
    public void transaktion(Akteur akteur, KonsumgutAngebot angebot) {
        if(!isAngebotValid(angebot)) {
            return;
        }
        Akteur lieferant = angebot.getAkteur();

        double kosten = (angebot.getVolumen() * angebot.getVerbrauchsGut().getPreis());
        if(akteur.hasAusreichendGeld(kosten)) {
            akteur.addBestand(angebot.getVerbrauchsGut(), angebot.getVolumen());
            lieferant.removeBestand(angebot.getVerbrauchsGut(), angebot.getVolumen());

            akteur.removeGeld(kosten);
            lieferant.addGeld(kosten);

            Logger.printKonsumgutErwerb(akteur, angebot);
        }
    }


    /**
     * Bedienung einer RohstoffAnfrage durch eine RohstoffLieferung, mit anschließendem
     * Update des Bestandes und Geldes beider Akteure.
     * @param rohstoffAnfrage RohstoffAnfrage welche bedient werden soll
     * @param rohstoffLieferung RohstoffLieferung entsprechend der Anfrage
     */
    public void transaktion(RohstoffAnfrage rohstoffAnfrage, RohstoffLieferung rohstoffLieferung) {
        //Akteur der RohstoffAnfrage
        Akteur anfrager = rohstoffAnfrage.getAkteur();
        //Akteur der RohstoffLieferung
        Akteur lieferant = rohstoffLieferung.getAkteur();

        VerbrauchsGut rohstoff = rohstoffLieferung.getVerbrauchsGut();
        int lieferVolumen = rohstoffLieferung.getVolumen();

        //Berechnung des Preises der Lieferung (Anzahl Rohstoffe * Rohstoffpreis)
        double kosten = (rohstoffLieferung.getVolumen() * rohstoffLieferung.getVerbrauchsGut().getPreis());

        //Prüfung, ob Akteur der RohstoffAnfrage genügend Geld hat.
        if(anfrager.hasAusreichendGeld(kosten)) {
            //Volumen der RohstoffLieferung wird in Bestand von anfrager hinzugefügt.
            anfrager.addBestand(rohstoff, lieferVolumen);
            //Volumen der RohstoffLieferung wird vom Bestand von lieferant abgezogen.
            lieferant.removeBestand(rohstoff, lieferVolumen);

            //Geld wird gebucht und entfernt.
            lieferant.addGeld(kosten);
            anfrager.removeGeld(kosten);

            //Ausgabe des Erwerbs.
            Logger.printRohstoffErwerb(rohstoffAnfrage, rohstoffLieferung);
        }
    }

    /**
     * Rückgabe der Singleton-Klasse
     * @return Marktplatz Singleton
     */
    public static Marktplatz getInstance() {
        return instance;
    }
}
