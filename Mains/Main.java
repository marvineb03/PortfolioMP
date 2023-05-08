package Mains;

import Akteure.*;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    //tet
    private static final ArrayList<Akteur> akteure = new ArrayList<>(); //ArrayList aller vorhandenen Akteure

    /**
     * Start-Methode
     *
     * @param args Startparameter
     */
    public static void main(String[] args) {
        //Initieren des Scanners zur Ausgabe von Nachrichten in der Konsole.
        Scanner();
        //Erzeugen aller benötigter Akteure.
        erzeugeAkteure();
        //Start der Simulation/Zeiteinheiten.
        starteSimulation();
    }

    /**
     * Methode, welche einen Scanner initiiert, welcher zu Beginn eine Eingabe ausliest.
     */
    private static void Scanner() {
        //Scanner mit Inputstream System.in
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ausgabe der Logs in der Konsole (true/false): ");
        //Lesen der Eingabe
        String input = scanner.nextLine();
        //Prüfung, ob Eingabe "true" oder "false" lautet.
        while (!input.equalsIgnoreCase("true") && !input.equalsIgnoreCase("false")) {
            //Falsche Eingabe. Hinweis auf korrekte Eingabe.
            System.out.println("Falsche Eingabe! Nutze: true/false");
            System.out.print("Ausgabe der Logs in der Konsole (true/false): ");
            //Erneute Aufforderung zur Eingabe.
            input = scanner.nextLine();
        }
        //Boolean aus Eingabe auswerten und an Logger übergeben.
        Logger.setAusgabe(Boolean.parseBoolean(input));
    }

    /**
     * Erzeugung aller Akteure der Simulation.
     */
    private static void erzeugeAkteure() {
        //ArrayList mit allen Konsumgüter Objekten
        ArrayList<Konsumgut> konsumGueter = erzeugeKonsumgueter();
        //Zählschleife, welche 3 Mal durchläuft.
        for (int i = 1; i < 4; i++) {
            //ArrayList mit Konsumgüter Objekten, welche an Produzenten übergeben werden sollen.
            ArrayList<Konsumgut> uebergabeGueter = new ArrayList<>();
            //Zählschleife, welche 2 Mal durchläuft.
            for (int j = 0; j < 2; j++) {
                //Zufällige Auswahl eines Konsumgutes.
                Konsumgut konsumgut = konsumGueter.get(new Random().nextInt(konsumGueter.size()));
                //Entfernen des Konsumgutes aus der Sammlung aller Güter.
                konsumGueter.remove(konsumgut);
                //Übergabe an ArrayList uebergabeGueter zur Übergabe an Produzent.
                uebergabeGueter.add(konsumgut);
            }

            //ArrayList<Akteur> akteure wird mit 3 verschiedenen Akteuren befüllt.
            akteure.add(new RohstoffLieferant("Rohstofflieferant-" + i, 5, 10));
            //ArrayList uebergabeGueter wird zu Konsumgut Array und an Produzent übergeben.
            akteure.add(new Produzent("Produzent-" + i, uebergabeGueter.toArray(new Konsumgut[0])));
            akteure.add(new Konsument("Konsument-" + i));
        }
    }

    /**
     * Start der Simulation und der Zeiteinheiten.
     */
    private static void starteSimulation() {
        //Aktuelle Zeiteinheit
        System.out.println("\n[EREIGNIS] Die Simulation wird gestartet.");
        int zeitEinheit = 0;
        //Prüfung, ob Zeiteinheit bereits 20x durchlaufen sind.
        while (zeitEinheit < 6) {
            zeitEinheit++;
             int finalzeitEinheit = zeitEinheit;
            //Pausieren des Threads für 1000 Millisekunden (1 Sekunde).
            //Abfangen einer möglichen InterruptedException des Threads.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.print("\n-------------- ZEITEINHEIT " + finalzeitEinheit + " --------------\n");
            //For-Schleife, welche alle Threads aller Akteure runned.
            for (Akteur akteur : akteure) {
                akteur.start();//sollte start sein , geht aber nicht

            }
        }

        System.out.println("\n\n[EREIGNIS] Die Simulation wird beendet.");
    }

    /**
     * Erzeugen aller zu produzierenden Konsumgüter.
     *
     * @return ArrayList mit allen erzeugten Konsumgüter
     */
    private static ArrayList<Konsumgut> erzeugeKonsumgueter() {
        ArrayList<Konsumgut> konsumGueter = new ArrayList<>();

        Konsumgut brot = new Konsumgut("Brot", 15, 1,
                new RohstoffVerbrauch(Rohstoff.WASSER, 2), new RohstoffVerbrauch(Rohstoff.MEHL, 2));
        konsumGueter.add(brot);

        Konsumgut croissant = new Konsumgut("Croissant", 17, 2,
                new RohstoffVerbrauch(Rohstoff.MEHL, 1), new RohstoffVerbrauch(Rohstoff.BUTTER, 2));
        konsumGueter.add(croissant);

        Konsumgut hefezopf = new Konsumgut("Hefezopf", 25, 1,
                new RohstoffVerbrauch(Rohstoff.HEFE, 2), new RohstoffVerbrauch(Rohstoff.ZUCKER, 2),
                new RohstoffVerbrauch(Rohstoff.BUTTER, 2));
        konsumGueter.add(hefezopf);

        Konsumgut broetchen = new Konsumgut("Brötchen", 15, 2,
                new RohstoffVerbrauch(Rohstoff.MEHL, 1), new RohstoffVerbrauch(Rohstoff.ZUCKER, 1));
        konsumGueter.add(broetchen);

        Konsumgut bier = new Konsumgut("Bier", 18, 1,
                new RohstoffVerbrauch(Rohstoff.HEFE, 2), new RohstoffVerbrauch(Rohstoff.WASSER, 3));
        konsumGueter.add(bier);

        Konsumgut kuchen = new Konsumgut("Kuchen", 35, 1,
                new RohstoffVerbrauch(Rohstoff.MEHL, 2), new RohstoffVerbrauch(Rohstoff.ZUCKER, 2),
                new RohstoffVerbrauch(Rohstoff.BUTTER, 2));
        konsumGueter.add(kuchen);

        return konsumGueter;
    }
}
