package org.example;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main { // Main klasse
    static List<ArbeitsPaket> arbeitsPaketListe = new ArrayList<>();            // initialisierung neue ArbeitsPaket typ liste mit name arbeitsPaketListe und deklarieren als Array liste

    private static boolean isNameSame(String name) {                             //name Exception
        for (ArbeitsPaket arbeitsPaket : arbeitsPaketListe) {           if (arbeitsPaket.getName().equalsIgnoreCase(name)) {           return true;     // falls name doppelt eingegeben ist
        }
        }
        return false;
    }

    private static boolean isNummerSame(int nummer) {                       // fals paket nummer bereits in liste vorhanden ist
        for (ArbeitsPaket arbeitsPaket : arbeitsPaketListe) {
            if (arbeitsPaket.getNummer() == nummer) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {                                            //Main Methode
        Scanner scanner = new Scanner(System.in);                                       //Scanner initialisierung
         legeBeispielArbeitspaketeAusMischokProjektBriefingAn();                        //falls nicht auskomenntiert ist, Arbeitspakete aus dem beispiel würden berechnet
        //lassUserKnotenEingeben(scanner);                                              // falls nicht auskomentiert ist, user kann eigene ArbeitsPakete eingeben

        for (ArbeitsPaket arbeitsPaket : arbeitsPaketListe) {                             // For schleife die schaut in arbeitsPaketListe für jedes arbeitPaket,
            for (ArbeitsPaket vorgaenger : arbeitsPaket.getVorgaenger()) {                //und weist dem paket einen Vorgänger und Nachfolger zu.
                vorgaenger.addNachFolger(arbeitsPaket);                                 //aktuele objekt ist einen Nachfolger zu dem Vorgenger
            }
        }

        System.out.println("Complete list");
        System.out.println(arbeitsPaketListe);                                          // komplete liste ausgabe an die Kommando zeile

        scanner.close();
    }

    private static void lassUserKnotenEingeben(Scanner scanner) {                       // Unsere Main methode die wir in lassUserKnotenEingeben umgewandelt haben.
                                                                                            //mit dem umwandlung ist möglich vorgegenbene beispiel ausgabe,so wie User eingabe
        while (true) {
            System.out.println("Package number: ");                                         //While schleife mit bedinung true die betreibt ganze program bis benutzer keine weiter eingabe hat(zeile 150-break)
            int nummer = 0;
            while (true) {
                try {                                                                        // exception : Der Number des Paket bereits existiert
                    nummer = scanner.nextInt();
                    scanner.nextLine();
                    if (isNummerSame(nummer)) {
                        System.out.println("Packet number already exits. Please try again: ");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {                                            // fals benutzer was anderes als paket number eingibt
                    System.out.println("Wrong input: Only numbers allowed. Try again:");
                    scanner.next();

                }
            }

            String name = "";
            while (true) {
                System.out.println("Please enter name: ");                                  //name eingabe
                name = scanner.nextLine();
                if (isNameSame(name)) {
                    System.out.println(" Name already exists. Please try again: ");             //Exception : Name ist bereit vergeben

                } else {
                    break;
                }
            }

            ArbeitsPaket vorgaengerArbeitPaket;                                                 // initialisierung des vorgaengerArbeitsPaket
            List<ArbeitsPaket> vorgaengerListe = new ArrayList<>();                              //initilissierung neu Array Liste von typ ArbeitsPaket die heißt vorgaengerListe
                                                                                                //in die Liste sind die Vorgänger des aktuele paket gespeichert
            if (!arbeitsPaketListe.isEmpty()) {                                                 //Das gilt für alle arbeitspakete bis zum ersten

                System.out.println("Bitte geben Sie Vorgängernummer ein. Lassen Sie der Feld leer, wenn es keine Vorgaenger gibt?");     //Vorgänger eingabe

                String vorgaengerInput;                                                                                                  // vorgaengerInput wird als eine String initialisiert

                while (true) {

                    try {
                        vorgaengerInput = scanner.nextLine();
                        if (vorgaengerInput.trim().isEmpty()) {
                            break;
                        }
                        String[] vorgaengerArray = vorgaengerInput.split(",");          // Split funktion lasst uns Mehrer vorgänger eingeben die sind mit komma getrennt


                        for (String nummStr : vorgaengerArray) {                                        //for each schleife die ist eine Exception,schaut ob der nummer gibts in die vorgaengerArray liste
                            int vorgeangerNummer = Integer.parseInt(nummStr.trim());                    // String eingabe wird ins integer umwandelt
                            vorgaengerArbeitPaket = getArbeitsPaketByNr(vorgeangerNummer);              // aufrufen von getArbeitsPaketByNr funktion ,die gibt vorgaengerNummer als nummer züruck
                            if (vorgaengerArbeitPaket != null) {                                        //wenn Vorgänger von aktuele objekt ist nicht null
                                vorgaengerListe.add(vorgaengerArbeitPaket);                             //das Vorgäenger wird zum liste zugewiesen
                            } else {                                                                    //Exception eingriff : den Vorgänger gibts nicht
                                System.out.println("Previous package with number " + vorgeangerNummer + " is not found");
                                throw new InputMismatchException("wrong Input. Try Again:");

                            }
                        }
                        break;

                    } catch (Exception e) {                                             //Main exception,der überschreibt alle andere
                        System.out.println(e.getMessage());


                    }
//
                }

            }
            System.out.println("Dauer : ");                                              //  Dauer eingabe
            double dauer = 0;                                                           //   Dauer initialisierung und deklarierung

            while (true) {
                try {

                    dauer = scanner.nextDouble() ;                                  //Dauer eingabe
                    scanner.nextLine();                                                 // nach komma zahlen eingabe
                    break;

                } catch (InputMismatchException e) {                                            //MismatchException e falls user buchstabe eingibt
                    System.out.println("Wrong input: Use numbers only." + " Try again:");
                    scanner.next();

                }

            }

            ArbeitsPaket arbeitsPaket = new ArbeitsPaket(nummer, name, dauer, vorgaengerListe);             //Konstruktor aufrufen

            for (ArbeitsPaket vorgaenger : vorgaengerListe) {                                               //Aktuele objekt arbeitsPaket wird zum vorgänger als nachfolger zugewiesen
                vorgaenger.addNachFolger(arbeitsPaket);
            }
            arbeitsPaketListe.add(arbeitsPaket);                                                            //Ins arbeitsPaketListe wird einen arbeitsPaket gespeichert

            System.out.println(arbeitsPaket);                                                               //Ausgabe jede Arbeits Paket nach vollstandige eingabe,damit lassen wir user überblick haben

            System.out.println("Do you have a new entry; y/n");                                             //Falls user n eingibt und keine neue eingabe hat
            String nextEntry = scanner.nextLine();
            if (nextEntry.equalsIgnoreCase("n")) {

                break;                                                                                      //While schleife wird beendet und alle Arbeits Pakete ausgegeben
            }
        }
    }
    private static ArbeitsPaket getArbeitsPaketByNr(int nummer) {                                           //Methode die gibt paket nummer züruck
        for (ArbeitsPaket arbeitsPaket : arbeitsPaketListe) {                                               // Hier wird in arbeitsPaketListe nachgeschaut und jede paket vergleicht mit dem eingegebene nummer
            if (arbeitsPaket.getNummer() == nummer) {
                return arbeitsPaket;                                                                        // falls bis zum treff kommt,das paket nummer wird züruck gegeben
            }
        }
        return null;
    }

    private static void legeBeispielArbeitspaketeAusMischokProjektBriefingAn() {
        ArbeitsPaket arbeitspaket1 = new ArbeitsPaket(1, "Server einbauen 1", 1, Collections.emptyList());
        ArbeitsPaket arbeitspaket2 = new ArbeitsPaket(2, "Betriebssystem installieren 2", 3, List.of(arbeitspaket1));
        ArbeitsPaket arbeitspaket3 = new ArbeitsPaket(3, "Updates installieren 3", 3, List.of(arbeitspaket2));
        ArbeitsPaket arbeitspaket4 = new ArbeitsPaket(4, "Rolle installieren 4", 1, List.of(arbeitspaket2));
        ArbeitsPaket arbeitspaket5 = new ArbeitsPaket(5, "Rolle konfigurieren 5", 5, List.of(arbeitspaket4));
        ArbeitsPaket arbeitspaket6 = new ArbeitsPaket(6, "Clients installieren 6", 3, Collections.emptyList());
        ArbeitsPaket arbeitspaket7 = new ArbeitsPaket(7, "Updates auf Clients installieren 7", 3, List.of(arbeitspaket6));
        ArbeitsPaket arbeitspaket8 = new ArbeitsPaket(8, "SW auf Clients ausrollen 8", 4, List.of(arbeitspaket6));
        ArbeitsPaket arbeitspaket9 = new ArbeitsPaket(9, "Modultests 9", 3, List.of(arbeitspaket3, arbeitspaket5));
        ArbeitsPaket arbeitspaket10 = new ArbeitsPaket(10, "Integrationstests 10", 3, List.of(arbeitspaket7, arbeitspaket8, arbeitspaket9));
        ArbeitsPaket arbeitspaket11 = new ArbeitsPaket(11, "Systemtests 11", 3, List.of(arbeitspaket10));
        arbeitsPaketListe.addAll(List.of(arbeitspaket1, arbeitspaket2, arbeitspaket3, arbeitspaket4, arbeitspaket5, arbeitspaket6, arbeitspaket7, arbeitspaket8, arbeitspaket9, arbeitspaket10, arbeitspaket11));
    }
}