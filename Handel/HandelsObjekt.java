package Handel;

import Akteure.Akteur;
import Akteure.VerbrauchsGut;

public class HandelsObjekt {
    private final Akteur akteur; //Akteur welcher HandelsObjekt erstellt.
    private final VerbrauchsGut verbrauchsGut; //VerbauchsGut, welches gehandelt werden soll.
    protected Integer volumen; //Anzahl von VerbrauchsGut

    /**
     *
     * @param akteur Akteur
     * @param verbrauchsGut VerbrauchsGut
     * @param volumen Integer
     *
     * Konstruktor der Klasse HandelsObejkt.
     * Übergabe der Konstruktor-Parameter an Klassenattribute.
     */
    public HandelsObjekt(Akteur akteur, VerbrauchsGut verbrauchsGut, Integer volumen) {
        this.akteur = akteur;
        this.verbrauchsGut = verbrauchsGut;
        this.volumen = volumen;
    }

    /**
     *
     * @return Akteur Attribut akteur
     *
     * Rückgabe des Akteur Attributes akteuer
     */
    public Akteur getAkteur() {
        return akteur;
    }

    /**
     *
     * @return VerbrauchsGut Attribut verbrauchsGut
     *
     * Rückgabe des VerbrauchsGut Attributes verbrauchsGut
     */
    public VerbrauchsGut getVerbrauchsGut() {
        return verbrauchsGut;
    }

    /**
     * @return Integer Attribut volumen
     * <p>
     * Rückgabe des Integer Attributs volumen
     */
    public int getVolumen() {
        return volumen;
    }
}
