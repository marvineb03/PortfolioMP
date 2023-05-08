package Handel;

import Akteure.Akteur;
import Akteure.VerbrauchsGut;

public class RohstoffAnfrage extends HandelsObjekt {

    /**
     *
     * @param produzent Akteur (Produzent)
     * @param verbrauchsGut VerbrauchsGut
     * @param volumen Integer Anzahl an Gütern
     *
     * Konstruktor der Klasse RohstoffAnfrage.
     * Übergabe der Konstruktor-Parameter an Klassenattribute.
     */
    public RohstoffAnfrage(Akteur produzent, VerbrauchsGut verbrauchsGut, Integer volumen) {
        super(produzent, verbrauchsGut, volumen);
    }

    /**
     *
     * @param volumen Höhe Volumen
     *
     * Methode zur Erhöhung des bereits gesetzten Integers volumen
     * der Elternklasse HandelsObjekt.
     */
    public void addVolumen(Integer volumen) {
        super.volumen += volumen;
    }
}