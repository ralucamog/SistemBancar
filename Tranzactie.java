/**
 * Clasa Tranzactie modelează transferul de fonduri între două conturi.
 * Rolul ei principal este de a transporta datele (Data Transfer Object).
 * CERINȚĂ: Încapsulare (private) și Redefinire metode Object (toString).
 */
public class Tranzactie {

    // 1. DATE MEMBRE PRIVATE (Principiul Încapsulării)
    //o dată    ce o tranzacție a fost creată, detaliile ei nu mai pot fi modificate.
    private String ibanSursa;
    private String ibanDestinatie;
    private double suma;

    /**
     * Constructor pentru inițializarea unei tranzacții.
     * @param ibanSursa IBAN-ul de unde pleacă banii
     * @param ibanDestinatie IBAN-ul unde ajung banii
     * @param suma Valoarea transferului
     */
    public Tranzactie(String ibanSursa, String ibanDestinatie, double suma) {
        this.ibanSursa = ibanSursa;
        this.ibanDestinatie = ibanDestinatie;
        this.suma = suma;
    }

    // 2. METODE PUBLICE DE ACCES (Getters)
    // doar Getters, deoarece o tranzacție este un "record" istoric
    // care nu ar trebui să se schimbe după ce a fost inițiată.

    public String getIbanSursa() {
        return ibanSursa;
    }

    public String getIbanDestinatie() {
        return ibanDestinatie;
    }

    public double getSuma() {
        return suma;
    }

    /**
     * 3. RESCRIEREA METODEI toString din clasa Object
     * Oferă o reprezentare vizuală a transferului
     */
    @Override
    public String toString() {
        return "Tranzactie: " + ibanSursa + " -> " + ibanDestinatie + " | Suma: " + suma + " RON";
    }
}