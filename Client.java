import java.util.ArrayList;
import java.util.List;

/**
 * Clasa Client este o clasă specializată care extinde clasa Persoana.
 * CERINȚE ÎNDEPLINITE:
 * 1. Moștenire (extends)
 * 2. Implementare Interfață (Comparable)
 * 3. Agregare (List<Cont>)
 * 4. Încapsulare (private)
 * 5. Redefinire metode Object (toString)
 */
// 1. "extends" indică MOȘTENIREA. Client preia automat atributele nume și varsta din Persoana.
// "implements Comparable" indică faptul că obiectele de tip Client pot fi SORTATE.
public class Client extends Persoana implements Comparable<Client> {

    // ÎNCAPSULARE: Atribut specific doar clasei Client, inaccesibil direct din exterior.
    private String codClient;

    /**
     * CERINȚĂ: AGREGAREA.
     * Clientul "are" o listă de conturi. Aceasta este o relație de tip "Has-A".
     * Folosim interfața 'List' pentru flexibilitate și 'ArrayList' pentru implementare.
     */
    private List<Cont> conturi;

    /**
     * 2. CONSTRUCTORUL clasei Client
     * @param nume - preluat de superclasa Persoana
     * @param varsta - preluat de superclasa Persoana
     * @param codClient - atribut propriu
     */
    public Client(String nume, int varsta, String codClient) {
        /*
         * CERINȚĂ: MOȘTENIRE (Utilizarea super).
         * "super" apelează constructorul din clasa mamă (Persoana).
         * Este OBLIGATORIU să fie prima instrucțiune din constructor.
         */
        super(nume, varsta);

        this.codClient = codClient;

        // Inițializăm lista de conturi (creăm containerul, dar este gol la început)
        this.conturi = new ArrayList<>();
    }

    // 3. METODE PUBLICE DE ACCES (Getters & Setters)
    public String getCodClient() {
        return codClient;
    }

    public void setCodClient(String codClient) {
        this.codClient = codClient;
    }

    /**
     * METODĂ PENTRU AGREGARE:
     * Permite adăugarea unui obiect de tip Cont în lista clientului.
     */
    public void adaugaCont(Cont cont) {
        this.conturi.add(cont);
    }

    public List<Cont> getConturi() {
        return conturi;
    }

    /**
     * 4. REDEFINIREA toString (Polimorfism)
     * CERINȚĂ: Rescrierea metodelor din clasa Object.
     */
    @Override
    public String toString() {
        /*
         * Folosim super.toString() pentru a refolosi logica deja scrisă în Persoana
         * (care returnează Nume și Vârstă), la care adăugăm datele specifice Clientului.
         */
        return super.toString() + ", Cod Client: " + codClient + ", Conturi: " + conturi;
    }

    /**
     * 5. LOGICA PENTRU SORTARE (Interfața Comparable)
     * CERINȚĂ: Colecție sortată (TreeSet-ul din ServiciuBancar are nevoie de această metodă).
     * @param altClient obiectul cu care comparăm clientul curent
     * @return un întreg: negativ (dacă e mai mic), zero (egal), pozitiv (mai mare)
     */
    @Override
    public int compareTo(Client altClient) {
        /*
         * Comparăm numele clienților alfabetic.
         * Folosim metoda compareTo deja existentă în clasa String din Java.
         */
        return this.getNume().compareTo(altClient.getNume());
    }
}