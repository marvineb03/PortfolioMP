package Handel;

import Akteure.Akteur;
import Akteure.VerbrauchsGut;

public class RohstoffLieferung extends HandelsObjekt {

    /**
     *
     * @param rohstoffLieferant Akteur (RohstoffLieferant)
     * @param verbrauchsGut VerbrauchsGut
     * @param volumen Integer Anzahl an Gütern
     *
     * Konstruktor der Klasse RohstoffLieferung.
     * Übergabe der Konstruktor-Parameter an Klassenattribute.
     */
    public RohstoffLieferung(Akteur rohstoffLieferant, VerbrauchsGut verbrauchsGut, Integer volumen) {
        super(rohstoffLieferant, verbrauchsGut, volumen);
    }
}
