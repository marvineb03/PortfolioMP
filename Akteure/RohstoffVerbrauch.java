package Akteure;

public class RohstoffVerbrauch {
    private final Rohstoff rohstoff; //Rohstoff welcher bei Produktion verbraucht wird.
    private final int volumen; //Anzahl des Rohstoffs, welcher verbraucht wird.


    /**
     *
     * @param rohstoff Rohstoff
     * @param volumen Anzahl des Rohstoffs
     *
     * Konstruktor der Klasse RohstoffVerbrauch.
     * Übergabe der Konstruktor-Parameter an Klassenattribute.
     */
    public RohstoffVerbrauch(Rohstoff rohstoff, int volumen) {
        this.rohstoff = rohstoff;
        this.volumen = volumen;
    }

    /**
     *
     * @return enum Attribut rohstoff
     *
     * Rückgabe des durch den Konstruktor festgelegten Rohstoffs.
     */
    public Rohstoff getRohstoff() {
        return this.rohstoff;
    }

    /**
     *
     * @return int Attribut volumen
     *
     * Rückgabe des durch den Konstruktor festgelegten Volumen.
     */
    public int getVolumen() {
        return this.volumen;
    }
}
