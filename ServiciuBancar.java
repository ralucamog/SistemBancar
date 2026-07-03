import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TreeSet;

/**
 * Această clasă reprezintă Layer-ul de Service.
   Aici este implementată logica de business și toate operațiile CRUD pe baza de date.
   Respectă principiul Single Responsibility, se ocupă doar de operațiunile bancare.
 */
public class ServiciuBancar {
    // Obiectul de conexiune la baza de date
    private Connection conexiune;

    public ServiciuBancar() {
        // CERINȚĂ: Utilizarea unui Design Pattern (Singleton)
        // Conexiunea unică prin Singleton-ul creat anterior pentru a economisi resurse.
        this.conexiune = ConfigurareBazaDate.getConexiune();
    }

    /**
     * CERINȚĂ: Operație de tip CREATE (CRUD)
     * Salvează un obiect de tip Client în tabelul 'clienti'.
     */
    public void salveazaClient(Client client) {
        // Query-ul SQL cu parametri de substituție (?)
        String sql = "INSERT INTO clienti (cod_client, nume, varsta) VALUES (?, ?, ?)";

        // CERINȚĂ: Securitate și bune practici
        // PreparedStatement pentru a preveni atacurile de tip SQL Injection.
        // Try-with-resources închide automat 'statement' la final, prevenind pierderile de memorie.
        try (PreparedStatement statement = conexiune.prepareStatement(sql)) {
            // Se mapează atributele obiectului Java pe coloanele tabelului
            statement.setString(1, client.getCodClient());
            statement.setString(2, client.getNume());
            statement.setInt(3, client.getVarsta());

            // Comanda de scriere
            statement.executeUpdate();
            System.out.println("Succes: Clientul " + client.getNume() + " a fost salvat în baza de date!");

            // CERINȚĂ: Audit CSV
            ServiciuAudit.log("salvare_client");
        } catch (SQLException e) {
            System.out.println("Eroare la salvarea clientului: " + e.getMessage());
        }
    }

    /**
      Salvează un obiect Cont și îl atribuie unui Client existent prin cod_client (Cheie Străină).
     */
    public void salveazaCont(Cont cont, String codClient) {
        String sql = "INSERT INTO conturi (iban, sold, cod_client) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexiune.prepareStatement(sql)) {
            stmt.setString(1, cont.getIban());
            stmt.setDouble(2, cont.getSold());
            stmt.setString(3, codClient);
            stmt.executeUpdate();
            System.out.println("Cont salvat: " + cont.getIban() + " pentru clientul " + codClient);

            ServiciuAudit.log("salvare_cont");
        } catch (SQLException e) {
            System.out.println("Eroare salvare cont: " + e.getMessage());
        }
    }

    /**
     * CERINȚĂ: Operație de tip UPDATE și complexitate logică.
     * Realizează un transfer între două conturi existente.
     */
    public void realizeazaTransfer(Tranzactie t) {
        //  3 operații SQL pentru un transfer complet
        String sqlScade = "UPDATE conturi SET sold = sold - ? WHERE iban = ?";
        String sqlAdauga = "UPDATE conturi SET sold = sold + ? WHERE iban = ?";
        String sqlLog = "INSERT INTO tranzactii (iban_sursa, iban_destinatie, suma) VALUES (?, ?, ?)";

        try {
            // Pasul 1: Debităm contul sursă
            try (PreparedStatement stmt1 = conexiune.prepareStatement(sqlScade)) {
                stmt1.setDouble(1, t.getSuma());
                stmt1.setString(2, t.getIbanSursa());
                stmt1.executeUpdate();
            }

            // Pasul 2: Credităm contul destinație
            try (PreparedStatement stmt2 = conexiune.prepareStatement(sqlAdauga)) {
                stmt2.setDouble(1, t.getSuma());
                stmt2.setString(2, t.getIbanDestinatie());
                stmt2.executeUpdate();
            }

            // Pasul 3: Salvăm istoricul tranzacției în alt tabel
            try (PreparedStatement stmt3 = conexiune.prepareStatement(sqlLog)) {
                stmt3.setString(1, t.getIbanSursa());
                stmt3.setString(2, t.getIbanDestinatie());
                stmt3.setDouble(3, t.getSuma());
                stmt3.executeUpdate();
            }

            System.out.println("Transfer realizat cu succes: " + t.getSuma() + " RON");

        } catch (SQLException e) {
            System.out.println("Eroare la procesarea tranzacției: " + e.getMessage());
        }
        ServiciuAudit.log("transfer_bancar");
    }

    /**
     * CERINȚĂ: Operație de tip READ (CRUD)
     * Citește toți clienții din baza de date și îi afișează.
     */
    public void afiseazaTotiClientii() {
        String sql = "SELECT * FROM clienti";
        // ResultSet-ul conține rândurile returnate de baza de date
        try (PreparedStatement stmt = conexiune.prepareStatement(sql);
             java.sql.ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Lista Clienti din Baza de Date ---");
            // Iterăm prin fiecare rând găsit
            while (rs.next()) {
                System.out.println("Cod: " + rs.getString("cod_client") +
                        " | Nume: " + rs.getString("nume") +
                        " | Varsta: " + rs.getInt("varsta"));
            }
            ServiciuAudit.log("afisare_toti_clientii");

        } catch (SQLException e) {
            System.out.println(" Eroare la citirea clienților: " + e.getMessage());
        }
    }

    /**
     * CERINȚĂ: Operație de tip DELETE (CRUD)
     * Șterge un client și toate datele asociate.
     */
    public void stergeClient(String codClient) {
        /*
         * Din cauza constrângerilor de Integritate Referențială (Foreign Key),
         * nu se poate șterge un client dacă are conturi legate de el.
         * De aceea, se șterge mai întâi conturile (tabelul copil) și apoi clientul (tabelul părinte).
         */
        String sqlConturi = "DELETE FROM conturi WHERE cod_client = ?";
        String sqlClient = "DELETE FROM clienti WHERE cod_client = ?";

        try {
            // Ștergem conturile
            try (PreparedStatement stmt1 = conexiune.prepareStatement(sqlConturi)) {
                stmt1.setString(1, codClient);
                stmt1.executeUpdate();
            }
            // Ștergem clientul
            try (PreparedStatement stmt2 = conexiune.prepareStatement(sqlClient)) {
                stmt2.setString(1, codClient);
                int randuriAfectate = stmt2.executeUpdate();

                if (randuriAfectate > 0) {
                    System.out.println("Clientul cu codul " + codClient + " a fost șters.");
                    ServiciuAudit.log("stergere_client");
                } else {
                    System.out.println("⚠️ Nu s-a găsit niciun client cu codul " + codClient);
                }
            }
        } catch (SQLException e) {
            System.out.println(" Eroare la ștergere: " + e.getMessage());
        }
    }

    /**
     * CERINȚĂ: A doua colecție diferită (TreeSet) + Sortare.
     * Această metodă extrage datele din SQL și le sortează în memoria Java.
     */
    public void afiseazaClientiSortati() {
        // TreeSet sortează automat elementele pe măsură ce sunt adăugate.
        // Necesită ca obiectul Client să implementeze interfața Comparable.
        TreeSet<Client> clientiSortati = new TreeSet<>();
        String sql = "SELECT * FROM clienti";

        try (PreparedStatement stmt = conexiune.prepareStatement(sql);
             java.sql.ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Transformă rândurile de tabel înapoi în obiecte Java (Deserializare manuală)
                Client c = new Client(rs.getString("nume"), rs.getInt("varsta"), rs.getString("cod_client"));
                clientiSortati.add(c);
            }

            System.out.println("\n--- Clienți sortați alfabetic (TreeSet) ---");
            // Iterăm prin colecția deja sortată de TreeSet
            for (Client c : clientiSortati) {
                System.out.println(c.getNume() + " | Cod: " + c.getCodClient());
            }
            ServiciuAudit.log("afisare_sortata_clienti");

        } catch (SQLException e) {
            System.out.println(" Eroare la sortare: " + e.getMessage());
        }
    }
}