package Handel;

import Akteure.Akteur;
import Akteure.VerbrauchsGut;

public class KonsumgutAngebot extends HandelsObjekt {

    /**
     *
     * @param akteur Akteur (Produzent)
     * @param verbrauchsGut VerbrauchsGut
     * @param volumen Integer Anzahl an Gütern
     *
     * Konstruktor der Klasse KonsumgutAngebot.
     * Übergabe der Konstruktor-Parameter an Klassenattribute.
     */
    public KonsumgutAngebot(Akteur akteur, VerbrauchsGut verbrauchsGut, Integer volumen) {
        super(akteur, verbrauchsGut, volumen);
    }
}
