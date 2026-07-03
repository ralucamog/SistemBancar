import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Această clasă gestionează conexiunea cu baza de date relațională (PostgreSQL).
 * Implementează Design Pattern-ul SINGLETON pentru a asigura o singură sursă de conexiune.
 */
public class ConfigurareBazaDate {
    // 1. Detaliile de conectare (Cerință: Persistență JDBC)
    // URL-ul conține protocolul (jdbc), motorul (postgresql), adresa (localhost), portul (5432) și numele bazei.
    private static final String URL = "jdbc:postgresql://localhost:5432/banca_pao";
    private static final String UTILIZATOR = "postgres";
    private static final String PAROLA = "studpao"; // Parola setată în pgAdmin

    // Variabilă statică ce va păstra instanța unică a conexiunii pe parcursul rulării programului
    private static Connection conexiune = null;

    /**
     * CONSTRUCTOR PRIVAT (Singleton)
     * Împiedică instanțierea clasei prin 'new ConfigurareBazaDate()' din exterior.
     * Singura cale de a obține conexiunea este prin metoda statică getConexiune().
     */
    private ConfigurareBazaDate() {}

    /**
     * Metodă de tip Factory care furnizează conexiunea unică (Lazy Initialization).
     * @return Obiectul de tip Connection necesar pentru interogările SQL.
     */
    public static Connection getConexiune() {
        // Verifică dacă este prima dată când se cere conexiunea
        if (conexiune == null) {
            try {
                // Încărcăm driverul și stabilim legătura folosind DriverManager
                conexiune = DriverManager.getConnection(URL, UTILIZATOR, PAROLA);
                System.out.println("Conexiune la baza de date 'banca_pao' a fost realizată cu succes!");
            } catch (SQLException e) {
                // Erorile SQL (ex: baza de date oprită, parola greșită)
                System.out.println("Eroare la conectare JDBC: " + e.getMessage());
            }
        }
        // Returnează conexiunea existentă (nu creăm una nouă la fiecare apel)
        return conexiune;
    }
}