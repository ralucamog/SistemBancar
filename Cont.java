/**
 * Clasa Cont modelează un cont bancar individual.
 * Aceasta este o clasă de tip Plain Old Java Object sau Entity, transportă date.
 * CERINȚĂ: Încapsulare (atribute private și metode publice de acces).
 */
public class Cont {

    // 1. DATE MEMBRE PRIVATE (Încapsulare)
    // Folosim 'private' pentru a restricționa accesul direct din alte clase.
    // Astfel, nicio altă clasă nu poate modifica soldul sau IBAN-ul la liber.
    private String iban;
    private double sold; // Soldul curent al contului

    /**
     * Constructor cu parametri pentru inițializarea obiectului Cont.
     * @param iban Codul unic al contului
     * @param sold Suma inițială depusă
     */
    public Cont(String iban, double sold) {
        this.iban = iban;
        this.sold = sold;
    }

    // 2. METODE DE ACCES (Getters și Setters)
    // Acestea permit citirea și modificarea controlată a datelor private.

    // Getter pentru IBAN - permite doar citirea, nu și modificarea (IBAN-ul e de obicei fix)
    public String getIban() {
        return iban;
    }

    // Getter pentru Sold - folosit în ServiciuBancar pentru a verifica suma disponibilă
    public double getSold() {
        return sold;
    }

    // Setter pentru Sold - singura cale prin care soldul se poate modifica în memoria Java
    public void setSold(double sold) {
        this.sold = sold;
    }

    /**
     * CERINȚĂ: Redefinirea metodelor din clasa Object (toString).
     * @Override anunță compilatorul că intenționăm să înlocuim metoda standard toString().
     * Fără această redefinire, System.out.println(cont) ar afișa o adresă de memorie ciudată.
     */
    @Override
    public String toString() {
        return "IBAN: " + iban + ", Sold: " + sold + " RON";
    }
}