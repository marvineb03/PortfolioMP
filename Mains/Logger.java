package Mains;

import Akteure.*;
import Handel.*;

import java.util.*;


public class Logger {
    private static boolean ausgabe = true; //Wert ob Ausgabe de- oder aktiviert ist.

    /**
     * Einfache Ausgabe von Strings.
     * @param line Nachricht
     */
    public static void print(String line) {
        //Prüfung, ob Ausgabe aktiv ist.
        if(!ausgabe) {
            return;
        }
        System.out.println(line);
    }

    /**
     * Ausgabe-Nachricht des Bestandes von Akteuren.
     * @param akteur Ziel-Akteur
     */
    public static void printBestand(Akteur akteur) {
        //Prüfung, ob Ausgabe aktiv ist.
        if(!ausgabe) {
            return;
        }

        //Zusammenbauen der Nachricht.
        StringBuilder builder = getStringBuilder("[BESTAND]" + akteur.getAnzeigeName() + " | Lager: ");
        
         
        //Iterator einzelener Map.Entry der HashMap bestand vom jeweiligen Akteur.
        Iterator<VerbrauchsGut> bestandIterator = akteur.getBestand().iterator() ;
       while (bestandIterator.hasNext()) {
            //Jeweilige Iterator-Zeile
        	 VerbrauchsGut x = bestandIterator.next();
            Integer verbrauchsGutBestand = akteur.getVGBestand(x);

            //Zusammenbauen der Nachricht.
             builder.append(verbrauchsGutBestand + "x " + x.getAnzeigeName()+ "");
                   

            //Prüfung, ob Iterator eine weitere Zeile hat.
            if(bestandIterator.hasNext()) {
                //Zusammenbauen der Nachricht.
            	builder.append(", ");
                
            }
        }
            //Zusammenbauen der Nachricht.
            System.out.println(builder);

                    }
        
    


    /**
     * Ausgabe-Nachricht des Bestandes von Akteuren.
     * @param anfrage Ziel-Akteur
     * @param lieferung Ziel-Akteur
     */
    public static void printRohstoffErwerb(RohstoffAnfrage anfrage, RohstoffLieferung lieferung) {
        //Prüfung, ob Ausgabe aktiv ist.
        if(!ausgabe) {
            return;
        }

        //Zusammenbauen der Nachricht.
        StringBuilder builder = getStringBuilder("[ROHSTOFF ERWERB] ")
                .append(lieferung.getAkteur().getAnzeigeName())
                .append(" --> ")
                .append(anfrage.getAkteur().getAnzeigeName())
                .append(" | Ware: ")
                .append(lieferung.getVolumen())
                .append("x ")
                .append(lieferung.getVerbrauchsGut().getAnzeigeName())
                .append(" | ")
                .append("Preis: ")
                .append(lieferung.getVolumen() * lieferung.getVerbrauchsGut().getPreis());

        //Ausgabe der Nachricht.
        System.out.println(builder);
    }

    /**
     *
     * @param akteur Akteur
     * @param konsumgutAngebot KonsumgutAngebot
     *
     * Ausgabe-Nachricht der Lieferung von Rohstoffen.
     */
    public static void printKonsumgutErwerb(Akteur akteur, KonsumgutAngebot konsumgutAngebot) {
        //Prüfung, ob Ausgabe aktiv ist.
        if(!ausgabe) {
            return;
        }

        //Zusammenbauen der Nachricht.
        StringBuilder builder = getStringBuilder("[KONSUMGUT ERWERB] ")
                .append(konsumgutAngebot.getAkteur().getAnzeigeName())
                .append(" --> ")
                .append(akteur.getAnzeigeName())
                .append(" | Ware: ")
                .append(konsumgutAngebot.getVolumen())
                .append("x ")
                .append(konsumgutAngebot.getVerbrauchsGut().getAnzeigeName())
                .append(" | ")
                .append("Preis: ")
                .append(konsumgutAngebot.getVolumen() * konsumgutAngebot.getVerbrauchsGut().getPreis());

        //Ausgabe der Nachricht.
        System.out.println(builder);
    }

    /**
     *
     * @param akteur Akteur
     * @param konsumgut Konsumgut
     *
     * Ausgabe-Nachricht der Produktion eines Konsumguts.
     */
    public static void printProduktion(Akteur akteur, Konsumgut konsumgut) {
        //Prüfung, ob Ausgabe aktiv ist.
        if(!ausgabe) {
            return;
        }

        //Zusammenbauen der Nachricht.
        StringBuilder builder = getStringBuilder("[PRODUKTION] ")
                .append(akteur.getAnzeigeName())
                .append(" | Ware: ")
                .append(konsumgut.getProduktionsVolumen())
                .append("x ")
                .append(konsumgut.getAnzeigeName());

        //Ausgabe der Nachricht.
        System.out.println(builder);
    }

    /**
     *
     * @param prefix String
     * @return StringBuilder Instanz
     *
     * Rückgabe eines StringBuilder Objektes, welches bereits einen String vordefiniert hat.
     */
    public static StringBuilder getStringBuilder(String prefix) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        return builder;
    }

    /**
     *
     * @param ausgabe boolean
     *
     * De-/Aktivieren der Ausgabe in die Konsole.
     */
    public static void setAusgabe(boolean ausgabe) {
        Logger.ausgabe = ausgabe;
    }
}
