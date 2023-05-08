package Akteure;

/**
 * @version 1.0
 * @author 
 * @see VerbrauchsGut
 *
 * Diese Klasse implementiert das Interface VerbrauchsGut
 * und dient als Datenobjekt zur Darstellung von produzierten Gütern
 * der Klasse Produzent.
 */
public class Konsumgut implements VerbrauchsGut {
    private final String anzeigeName; //Name für Ausgabe
    private final double preis; //Preis zum Kauf des Konsumguts durch Konsument
    private final RohstoffVerbrauch[] rohstoffVerbrauch; //Rohstoffverbrauch durch Produzent pro Produktion
    private final int produktionsVolumen; //Anzahl produzierter Konsumgüter pro Produktion und Rohstoffverbrauch

    /**
     *
     * @param anzeigeName Name des Konsumguts
     * @param preis Preis des Konsumguts
     * @param produktionsVolumen Anzahl der produizierten Konsumgüter pro Zeiteinheit
     * @param rohstoffVerbrauch Array mit Rohstoffverbrauch der Produktion
     *
     * Konstruktor der Klasse Konsumgut.
     * Übergabe der Konstruktor-Parameter an Klassenattribute.
     */
    public Konsumgut(String anzeigeName, double preis, int produktionsVolumen, RohstoffVerbrauch... rohstoffVerbrauch) {
        this.anzeigeName = anzeigeName;
        this.preis = preis;
        this.produktionsVolumen = produktionsVolumen;
        this.rohstoffVerbrauch = rohstoffVerbrauch;
    }

    /**
     *
     * @return Array Attribut RohstoffVerbrauch
     * @see RohstoffVerbrauch
     *
     * Durch Interface VerbrauchsGut implementiert.
     * Rückgabe des durch den Konstruktor festgelegten Array an RohstoffVerbrauch.
     */
    public RohstoffVerbrauch[] getRohstoffVerbrauch() {
        return this.rohstoffVerbrauch;
    }

   

    /**
     *
     * @return String Attribut anzeigeName
     *
     * Durch Interface VerbrauchsGut implementiert.
     * Rückgabe des durch den Konstruktor festgelegten Anzeigenamen.
     */
    @Override
    public String getAnzeigeName() {
        return this.anzeigeName;
    }

    /**
     *
     * @return double Attribut preis
     *
     * Durch Interface VerbrauchsGut implementiert.
     * Rückgabe des durch den Konstruktor festgelegten Preis.
     */
    @Override
    public double getPreis() {
        return this.preis;
    }

    /**
     *
     * @return int Attribut produktionsVolumen
     *
     * Rückgabe des durch den Konstruktor festgelegten Produktionsvolumen.
     */
    public int getProduktionsVolumen() {
        return produktionsVolumen;
    }
}
