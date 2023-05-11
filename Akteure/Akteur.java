package Akteure;

import java.util.ArrayList;

/**
 * @version 1.0
 * @author marv 
 * @see Thread
 *
 * Diese Klasse extenden die Klasse Thread und bildet die Basis aller Akteure.
 *
 */
public class Akteur extends Thread {
    private double geld; //Geld des Akteurs
    private final String anzeigeName; //Anzeigename des Akteurs
    protected  ArrayList<VerbrauchsGut> bestand; 
    /**
     *
     * @param anzeigeName Name des Akteurs
     *
     * Konstruktor der Klasse Akteur.
     * Übergabe der Konstruktor-Parameter an Klassenattribute.
     */
    public Akteur( String anzeigeName) {
        //geld -1 = unbegrenzt
        this.geld = -1;
        this.anzeigeName = anzeigeName;
        this.bestand = new ArrayList<>();
    }

    /**
     *
     * @return String Attribut anzeigeName
     *
     * Rückgabe des String Attributes anzeigeName.
     */
    public String getAnzeigeName() {
        return anzeigeName;
    }

    /**
     *
     * @param verbrauchsGut VerbrauchsGut
     * @param anzahl Integer
     *
     * Bestandserweiterung eines Verbrauchguts.
     */
    public synchronized void addBestand(VerbrauchsGut verbrauchsGut, int anzahl) {

        for (int i = 0; i < anzahl; i++) {
           this.bestand.add(verbrauchsGut);
        }

    }

    /**
     *
     * @param verbrauchsGut VerbrauchsGut
     * @param anzahl Integer
     *
     * Bestandsminderung eines Verbrauchguts.
     */
    public synchronized void removeBestand(VerbrauchsGut verbrauchsGut, int anzahl) {
        //Prüfung, ob VerbrauchsGut im Bestand ist.
       // if(hasBestand(verbrauchsGut)) {
            //Bestandsminderung
            //int neuerBestand = this.bestand.size() - anzahl;
         //   this.bestand.remove(verbrauchsGut);
      //  }

        for ( int i = 0 ; i < this.bestand.size(); i++){
            if(anzahl == 0  || !this.bestand.contains(verbrauchsGut)){
                break;
            }
            this.bestand.remove(verbrauchsGut);
            anzahl--;
        }


    }

    /**
     *
     * @param verbrauchsGut VerbrauchsGut
     * @return boolean
     *
     * Prüfung, ob sich ein VerbrauchsGut im Bestand befindet.
     */
    public boolean hasBestand(VerbrauchsGut verbrauchsGut) {

        return this.bestand.contains(verbrauchsGut);
    }

    /**
     *
     * @param verbrauchsGut VerbrauchsGut
     * @return Integer Bestand
     *
     * Rückgabe Anzahl Bestand eines Verbrauchguts
     */
    public synchronized int getVGBestand(VerbrauchsGut verbrauchsGut) {
        int count = 0;
        for(VerbrauchsGut gut : this.bestand){
            if(gut == verbrauchsGut){
                count++;
            }
        }

        return count;
    }

    /**
     *
     * @param geld double
     * @return boolean
     *
     * Prüfung, ob Akteur genügend Geld in Relation zu param geld besitzt.
     */
    public boolean hasAusreichendGeld(double geld) {
        if(this.geld == -1D) {
            return true;
        }
        return ((this.geld - geld) >= 0);
    }

    /**
     *
     * @param geld double
     *
     * Erhöhung Anzahl Geld
     */
    public void addGeld(double geld) {
        //Prüfung, ob Geld unbegrenzt ist.
        if(this.geld == -1D) {
            return;
        }
        //Erhöung Geld
        this.geld += geld;
    }

    /**
     *
     * @param geld double
     *
     * Minderung Anzahl Geld
     */
    public void removeGeld(double geld) {
        //Prüfung, ob Geld unbegrenzt ist.
        if(this.geld == -1D) {
            return;
        }
        //Minderung Geld
        this.geld -= geld;
    }

    /**
     *
     * @return HashMap<VerbrauchsGut, Integer>
     *
     * Rückgabe HashMap<VerbrauchsGut, Integer> bestand
     */
    public synchronized  ArrayList<VerbrauchsGut> getBestand() {

        return this.bestand;
    }
}
