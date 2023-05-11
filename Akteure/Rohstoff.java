package Akteure;


/**
 * @version 1.0
 * @author 
 *
 * Diese enum Klasse dient als Datenobjekt für Rohstoffe, deren Anzeigenamen und Preise.
 */
public enum Rohstoff implements VerbrauchsGut {
    MEHL("Mehl", 4.0),
    ZUCKER("Zucker", 3.5),
    WASSER("Wasser", 1.0),
    HEFE("Hefe", 6.0),
    BUTTER("Butter", 5.0);

    private final String anzeigeName;
    private final double preis;

    /**
     *
     * @param anzeigeName Anzeigename des Rohstoffs
     * @param preis Preis des Rohstoffs
     *
     * Konstruktor der Klasse Rohstoff.
     * Übergabe der Konstruktor-Parameter an Klassenattribute.
     */
    Rohstoff(String anzeigeName, double preis) {
        this.anzeigeName = anzeigeName;
        this.preis = preis;
    }

    

    /**
     *
     * @return Akteur Attribut akteur
     *
     * Rückgabe des Akteur Attributes akteuer.
     */
    @Override
    public synchronized String getAnzeigeName() {
        return anzeigeName;
    }

    /**
     *
     * @return double Attribut preis
     *
     * Rückgabe des double Attributes preis.
     */
    @Override
    public double getPreis() {
        return preis;
    }
}
