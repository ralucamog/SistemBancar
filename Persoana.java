/**
 * Clasa Persoana reprezintă Superclasa (clasa părinte) proiectului.
 * Aceasta este o clasă de bază care încapsulează trăsăturile universale ale unei persoane.
 */
public class Persoana {

    // 1. DATE MEMBRE PROTECTED - Principiul Încapsulăriiși și Moștenirii
    // Folosim 'protected' pentru a ascunde datele de restul programului (cum ar fi clasa Main),
    // dar pentru a permite ACCESUL DIRECT din subclasa 'Client' (care extinde această clasă).
    // Astfel, asigurăm integritatea datelor, dar păstrăm relația de moștenire.
    protected String nume;
    protected int varsta;

    /**
     * 2. CONSTRUCTOR pentru inițializarea datelor.
     * Este apelat automat la crearea unui obiect (sau prin 'super()' din subclase).
     * @param nume Numele persoanei
     * @param varsta Vârsta persoanei
     */
    public Persoana(String nume, int varsta) {
        // 'this' face referire la atributul clasei curent pentru a-l distinge de parametrul metodei
        this.nume = nume;
        this.varsta = varsta;
    }

    // 3. METODE PUBLICE DE ACCES (Getters & Setters)

    // Getter: Returnează valoarea numelui
    public String getNume() {
        return nume;
    }

    // Setter: Permite modificarea numelui (util dacă un client își schimbă numele legal)
    public void setNume(String nume) {
        this.nume = nume;
    }

    // Getter: Returnează vârsta
    public int getVarsta() {
        return varsta;
    }

    // Setter: Permite actualizarea vârstei
    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    /**
     * 4. RESCRIEREA METODEI toString din clasa Object
     * @Override este o adnotare care confirmă că suprascriem metoda moștenită de la Object.
     * Această metodă transformă starea obiectului într-un format text citibil.
     */
    @Override
    public String toString() {
        // Returnăm un format clar care va fi folosit de System.out.println
        return "Nume: " + nume + ", Varsta: " + varsta;
    }
}