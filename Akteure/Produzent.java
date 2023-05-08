package Akteure;

import Handel.*;
import Mains.*;

import java.util.ArrayList;

/**
 * @version 1.0
 * @author 
 * @see Akteur
 * @see Konsumgut
 * @see Marktplatz
 *
 * Diese Klasse implementiert das Interface Akteur
 * und dient dazu Konsumgüter zu produzieren und diese auf dem Marktplatz anzubieten.
 */
public class Produzent extends Akteur {
    private final Konsumgut[] konsumGueter; //Array der zu produzierenden Konsumgüter pro Zeiteinheit
    private final ArrayList<RohstoffAnfrage> anfragenToGo; //ArrayList der zusendenden Anfragen nach Produktion

    /**
     *
     * @param anzeigeName Name des Produzenten
     * @param konsumGueter Array der zu produzierenden Konsumgüter
     *
     * Konstruktor der Klasse Produzent.
     * Übergabe der Konstruktor-Parameter an Klassenattribute.
     */
    public Produzent(String anzeigeName, Konsumgut[] konsumGueter) {
        //Aufruf Konstruktor der Elternklasse Akteur
        super(anzeigeName);
        this.konsumGueter = konsumGueter;

        this.anfragenToGo = new ArrayList<>();

        //Jedes Konsumgut des Arrays konsumGueter wird mit der Anzahl 0 in den Bestand eingefügt.
        for(Konsumgut konsumgut : konsumGueter) {
            super.addBestand(konsumgut, 0);
        }
    }

    /**
     * Run Methode der Klasse Thread.
     * Methode wird pro Zeiteinheit einmal ausgeführt.
     */
    @Override
    public void run() {
        //Jedes Konsumgut des Arrays konsumGueter wird durchgegangen.
        for (Konsumgut konsumgut : this.konsumGueter) {
            //Prüfung des Bestandes, ob Produktion möglich ist.
            boolean bestandAusreichend = isProduktionMoeglich(konsumgut);

            //Produktion, falls genügend Bestand.
            if(bestandAusreichend) {
                RohstoffVerbrauch[] rohstoffVerbrauche = konsumgut.getRohstoffVerbrauch();
                //Jeder Rohstoffverbauch des Konsumguts wird durchgegangen.
                for(RohstoffVerbrauch rohstoffVerbrauch : rohstoffVerbrauche) {
                    Rohstoff rohstoff = rohstoffVerbrauch.getRohstoff();
                    Integer volumen = rohstoffVerbrauch.getVolumen();

                    //Rohstoff mit entsprechendem Volumen wird aus dem Bestand entfernt.
                    super.removeBestand(rohstoff, volumen);
                }

                //Anzahl des zu produzierenden Konsumguts
                int produktionsVolumen = konsumgut.getProduktionsVolumen();
                //Konsumgut wird mit entsprechendem Volumen in den Bestand hinzugefügt.
                super.addBestand(konsumgut, produktionsVolumen);

                //Ein Angebot für das produzierte Konsumgut mit entsprechendem Volumen wird erstellt.
                KonsumgutAngebot angebot = new KonsumgutAngebot(this, konsumgut, produktionsVolumen);
                //Angebot wird an den Marktplatz übergeben.
                Marktplatz.getInstance().stelleAngebot(angebot);

                //Ausgabe des produzierten Konsumguts.
                Logger.printProduktion(this, konsumgut);
            }

            //Erstellen der Anfrage mit benötigten Rohstoffen.
            buildAnfrage(konsumgut);
        }

        //Anfragen der ArrayList anfragenToGo werden an den Marktplatz übergeben.
        sendAnfragen();
       // Logger.printBestand(this);
    }

    private boolean isProduktionMoeglich(Konsumgut konsumgut) {
        boolean success = true;

        //Prüfung des Bestandes jedes einzelnen Rohstoffverbrauch von konsumgut.
        for(RohstoffVerbrauch rohstoffVerbrauch : konsumgut.getRohstoffVerbrauch()) {
            Rohstoff rohstoff = rohstoffVerbrauch.getRohstoff();
            //Interner Bestand des Rohstoffs.
            int bestand = super.getVGBestand(rohstoff);

            /**
             * Prüfung, ob Bestand von Akteur für den Rohstoffverbrauch ausreicht.
             * Falls nicht, abbruch und return false.
             */
            if(bestand < rohstoffVerbrauch.getVolumen()) {
                success = false;
                break;
            }
        }

        return success;
    }

    private void buildAnfrage(Konsumgut konsumgut) {
        //Prüfung des Bestandes jedes einzelnen Rohstoffverbrauch von konsumgut.
        for(RohstoffVerbrauch rohstoffVerbrauch : konsumgut.getRohstoffVerbrauch()) {

            Rohstoff rohstoff = rohstoffVerbrauch.getRohstoff();
            Integer benoetigtesVolumen = rohstoffVerbrauch.getVolumen();

            Integer aktuellerBestand = super.getVGBestand(rohstoff);

            //Prüfung, ob aktuellerBestand unter aktuellem benoetigtesVolumen liegt.
            if(aktuellerBestand < benoetigtesVolumen) {

                //Prüfung, ob bereits eine Anfrage für aktuellen Rohstoff vom Produzent vorliegt.
                if(existMartkplatzAnfrage(rohstoff)) {
                    continue;
                }

                //Prüfung, ob bereits Anfrage für Rohstoff in ArrayList anfragenToGo vorliegt.
                RohstoffAnfrage anfrage = findAnfrageDuplikate(rohstoff);

                //Prüfung, ob Ergebnis des Duplikat-Checks null ist.
                if(anfrage == null) {
                    //Erstellung einer neuen Anfrage mit rohstoff und benoetigtesVolumen.
                    anfrage = new RohstoffAnfrage(this, rohstoff, benoetigtesVolumen);

                    this.anfragenToGo.add(anfrage);
                } else {
                    //Anfrage zum Rohstoff rohstoff exisiter bereits. Volumen der Anfrage wird erhöht.
                    anfrage.addVolumen(rohstoffVerbrauch.getVolumen());
                }
            }
        }
    }


    /**
     *
     * @param verbrauchsGut
     * @return Anfrage Objekt || null
     *
     * Prüfung, ob bereits eine Anfrage des entsprechenden VerbrauchsGut in ArrayList anfrageToGo vorliegt.
     */
    private RohstoffAnfrage findAnfrageDuplikate(VerbrauchsGut verbrauchsGut) {
        //Objekt Anfrage ist grundsätzlich null. Null-State bleibt, falls keine Anfrage gefunden wird.
        RohstoffAnfrage result = null;

        //For-Schleife, welche jedes Objekt der ArrayList anfragenToGo durchgeht.
        for (RohstoffAnfrage anfrage : this.anfragenToGo) {
            //Prüfung ob VerbrauchsGut der Anfrage dem Parameter verbrauchsGut entspricht.
            if(anfrage.getVerbrauchsGut().equals(verbrauchsGut)) {
                //Null-State von result wird aufgehoben und Schleife abgebrochen.
                result = anfrage;
                break;
            }
        }

        return result;
    }

    /**
     *
     * @param verbrauchsGut
     * @return boolean
     *
     * Prüfung, ob bereits eine zum VerbrauchsGut entsprechende Anfrage am Marktplatz vorliegt.
     */
    private boolean existMartkplatzAnfrage(VerbrauchsGut verbrauchsGut) {
        //ArrayList mit Anfragen, welche von diesem Akteur am Marktplatz gestellt wurden.
        ArrayList<RohstoffAnfrage> anfragenByAkteur = Marktplatz.getInstance().getAnfragenByAkteur(this);

        //For-Schleife, welche jedes Objekt der ArrayList anfragenByAkteur durchgeht.
        for (RohstoffAnfrage anfrage : Marktplatz.getInstance().getAnfragenByAkteur(this)) {
            //Prüfung, ob VerbrauchsGut der Anfrage mit dem Parameter verbrauchsGut übereinstimmt.
            if(anfrage.getVerbrauchsGut().equals(verbrauchsGut)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Übergabe der Anfragen der ArrayList anfragenToGo an Marktplatz
     */
    private void sendAnfragen() {
        //For-Schleife, welche jedes Objekt der ArrayList anfragenToGo durchgeht.
        for(RohstoffAnfrage anfrage : this.anfragenToGo) {
            //Übergabe jeder Anfrage an Marktplatz.
            Marktplatz.getInstance().stelleAnfrage(anfrage);
        }
        //ArrayList anfragenToGo wird geleert.
        this.anfragenToGo.clear();
    }
}
