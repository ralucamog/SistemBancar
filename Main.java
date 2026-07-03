import java.util.Scanner; //Se importă utilitarul pentru citirea datelor de la tastatură

/**
 * Clasa Main este punctul de intrare (Entry Point) în aplicație.
 * Aceasta implementează Interfața cu Utilizatorul (UI) sub formă de Meniu Text
 */

public class Main {
    public static void main(String[] args) {
        //Se initializeaza Scanner-ul pentru a prelua datele de intrare de la utilizator
        Scanner scanner = new Scanner(System.in);
        // Instanța serviciului bancar care va face legătura între interfață și baza de date (Persistence Layer)
        ServiciuBancar service = new ServiciuBancar();

        System.out.println(" SISTEM BANCAR PAO 2026 ");

        // 1. Facilitate de Autentificare
        //Apelare metoda privată autentificare
        if (!autentificare(scanner)) {
            System.out.println("Autentificare eșuată. Programul se închide.");
            return;
        }
        //Contorizare autentificare reuțită în CSV
        ServiciuAudit.log("autentificare_reusita");

        //Meniul Principal
        // Variabila rulare menține aplicația activă până când utilizatorul alege Ieșire
        boolean rulare = true;
        while (rulare) {
            System.out.println("\n * MENIU PRINCIPAL *");
            System.out.println("1. Adaugă Client Nou");
            System.out.println("2. Adaugă Cont la Client existent");
            System.out.println("3. Realizează Transfer Bancar");
            System.out.println("4. Afișează toți clienții (Lista simplă)");
            System.out.println("5. Afișează clienții sortați alfabetic (TreeSet)");
            System.out.println("6. Șterge un Client");
            System.out.println("0. Ieșire");
            System.out.print("Alege opțiunea: ");

            int optiune = scanner.nextInt();
            /* * După nextInt(), rămâne un caracter 'newline' (\n) în buffer.
             Trebuie consumat cu nextLine() pentru ca următoarele citiri de text să nu fie sărite eronat de Java
             */
            scanner.nextLine();

            // Structura de decizie pentru execuția acțiunilor
            switch (optiune) {
                case 1:
                    // ACȚIUNE: Salvare Client (Create din CRUD)
                    System.out.print("Nume client: "); String nume = scanner.nextLine();
                    System.out.print("Vârstă: "); int varsta = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Cod Client (ex: BC001): "); String cod = scanner.nextLine();
                    // Se creează obiectul anonim new Client și se trimite spre salvare în Baza de Date
                    service.salveazaClient(new Client(nume, varsta, cod));
                    break;
                case 2:
                    //  ACȚIUNE: Salvare Cont (Agregare)
                    System.out.print("Cod Client pentru care adăugați cont: "); String cdl = scanner.nextLine();
                    System.out.print("IBAN: "); String iban = scanner.nextLine();
                    System.out.print("Sold inițial: "); double sold = scanner.nextDouble();

                    // Se asociaza noul cont de codul clientului introdus
                    service.salveazaCont(new Cont(iban, sold), cdl);
                    break;
                case 3:
                    // ACȚIUNE: Transfer Bancar (Update din CRUD)
                    System.out.print("IBAN Sursă: "); String sursa = scanner.nextLine();
                    System.out.print("IBAN Destinație: "); String dest = scanner.nextLine();
                    System.out.print("Suma de transferat: "); double suma = scanner.nextDouble();

                    // Se încapsulează datele într-un obiect Tranzactie pentru a le trimite spre procesare
                    service.realizeazaTransfer(new Tranzactie(sursa, dest, suma));
                    break;
                case 4:
                    // ACȚIUNE: Citire Date (Read din CRUD)
                    // Afișează toți clienții in ordinea Bazei de Date
                    service.afiseazaTotiClientii();
                    break;
                case 5:
                    //  ACȚIUNE: Sortare (Cerință: Colecție Sortată / TreeSet)
                    // Această metodă va folosi un TreeSet pentru a ordona clienții alfabetic
                    service.afiseazaClientiSortati();
                    break;
                case 6:
                    // ACȚIUNE: Ștergere (Delete din CRUD)
                    System.out.print("Introduceți codul clientului de șters: "); String deSters = scanner.nextLine();
                    service.stergeClient(deSters);
                    break;
                case 0:
                    //IEȘIRE
                    System.out.println("Vă mai așteptăm! Se închide...");
                    ServiciuAudit.log("iesire_aplicatie");
                    rulare = false; //se oprește bucla while
                    break;
                default:
                    // Gestionarea input-ului greșit de la tastatură
                    System.out.println("Opțiune invalidă!");
            }
        }
        scanner.close();
    }

    /** Metodă simplă de autentificare
     @param scanner Obiectul scanner deja deschis în main
     @return true dacă datele coincid cu cele prestabilite
     */
    private static boolean autentificare(Scanner scanner) {
        System.out.print("Utilizator: ");
        String user = scanner.nextLine();
        System.out.print("Parolă: ");
        String pass = scanner.nextLine();

        // Se verifica un user și o parolă prestabilite
        return user.trim().equalsIgnoreCase("admin") && pass.trim().equals("pao2026");
    }
}