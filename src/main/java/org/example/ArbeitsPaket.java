package org.example;

import java.util.ArrayList;
import java.util.List;

class ArbeitsPaket {
    private final int nummer;
    private final  String name;
    private final double dauer;
    private final List<ArbeitsPaket> vorgaenger;
    private final  List<ArbeitsPaket> nachFolgerList;

    public ArbeitsPaket(int nummer, String name, double dauer, List<ArbeitsPaket> vorgaenger) {
        this.nummer = nummer;
        this.name = name;
        this.dauer = dauer;
        this.vorgaenger = vorgaenger;
        this.nachFolgerList = new ArrayList<>();
    }
    public double getDauer() {
        return dauer;
    }
    public List<ArbeitsPaket> getVorgaenger() {
        return vorgaenger;
    }
    public void addNachFolger(ArbeitsPaket nachFolger) {
        nachFolgerList.add(nachFolger);
    }

    @Override
    public String toString() {                                                                                                               // die methode gibt nur paket nummer züruck
        StringBuilder vorNummer = new StringBuilder();                                                                                                                 // ohne toString hätte ganze knoten ausgegeben
        for (ArbeitsPaket ap : vorgaenger) {
            vorNummer.append(ap.getNummer()).append(", ");
        }
        StringBuilder nachNummer = new StringBuilder();                                                                                                                //die Methode gibt nachfolger pakete nummer züruck
        for (ArbeitsPaket ap : nachFolgerList) {                                                                                                //ohne toString methode hätte ganze knoten (arbeitspaket) ausgegeben
            nachNummer.append(ap.getNummer()).append(", ");                                                                                     //es hätte kaotisch ausschauen
        }
        return  "   Paket nummer "+getNummer()+"                                                                                         |  \n"+    //toString methode lasst uns kleine grafische oberflache erstellen
                "                                                                                                                                                        \n"+
                " |   Name " + name + "         |   Dauer : " + getDauer() + "   |   Vorgänger: " + vorNummer + "  Nachfolger Paket nummer "+nachNummer+"      |\n" +
                " |   FAZ  " + calculateFAZ() + "                 |  GP : " +calculateGP()+ "       |   FEZ " + calculateFEZ() + "            |\n" +
                " |   SAZ  " + calculateSAZ() + "                 |  FP : " + calculateFP()+"       |   SEZ " + calculateSEZ() + "             |\n" +
                "                                                                                                                            \n";
    }
    public int getNummer() {
        return nummer;
    }                                                       // nummer getter
    public double calculateFEZ() {
        return calculateFAZ() + getDauer();                                                         // FEZ berechnung methode = FAZ - Dauer
    }
    public double calculateFAZ() {
        if (vorgaenger.isEmpty()) {                                                                 //Fals erste paket ist dann der Paket hat FAZ 0 immer
            return 0;
        } else {
            double maxFez = 0;
            for (ArbeitsPaket ap : vorgaenger) {                                                   //sonst wird ins vorganger Liste nachgeschaut ob der Paket vorgaenger hat
                double vorgaengerFez = ap.calculateFEZ();
                if (vorgaengerFez > maxFez) {                                                       //der großesete FEZ von mehrer pakete wird genommen
                    maxFez = vorgaengerFez;                                                         //und als max FEZ züruckgegeben
                }
            }
            return maxFez;
        }
    }

    public double calculateSEZ() {
        if (nachFolgerList.isEmpty()) {                                 // falls es um letzte paket geht der keinen nachfolger hat
            return calculateFEZ();                                      //FEZ von dem Paket züruck geben
        }
        double minSAZ = Double.MAX_VALUE;                               //Sonst wir vergleichen die nachfolger paket SAZ und schauen wer kleinste ist
        for (ArbeitsPaket nachfolger : nachFolgerList) {                //Kleinste wird übergenommen
            double nachfolgerSaz = nachfolger.calculateSAZ();
            if (nachfolgerSaz < minSAZ) {
                minSAZ = nachfolgerSaz;
            }
        }
        return minSAZ;
    }
    public double calculateSAZ() {
        return calculateSEZ() - getDauer();
    }       // SAZ = SEZ-Dauer
    public double calculateGP(){                                               //GP=SEZ-FEZ
        double GP = calculateSEZ()-calculateFEZ();
        return GP;
    }
    public double calculateFP() {
        if (!nachFolgerList.isEmpty()) {                                        // if list is empty,wenn letzte paket ist
            double minFAZ = Double.MAX_VALUE;                                       //mussen wir bemesung parameter haben der ist grosß
            for (ArbeitsPaket nachfolger : nachFolgerList) {                       //objekt nachfolger geht dürch ganze arbeitspaket liste
                double nachfolgerFAZ = nachfolger.calculateFAZ();                   //zum nachfolgerFAZ wird nachfolger sein FAZ übergenommen
                if (nachfolgerFAZ < minFAZ) {                                         //hier wird nachfolger vergleicht mit großtem nachfolger bis jetz der da ist
                    minFAZ = nachfolgerFAZ;                                             //zu dem minFaz wird nachfolgerFAZ zugewiesen
                }
            }
            return minFAZ - calculateFEZ();                                         //faz des kleinste objekt - FEZ ist gleich unsere FP
        }
        else {
            return 0;                                                               // wenn es um letzte paket geht dann fp ist 0,bzw wenn nachfolger liste leer ist
        }
    }
    public String getName(){
        return name;
    }
}