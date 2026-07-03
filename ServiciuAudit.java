import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Această clasă utilitară se ocupă de jurnalizarea (auditarea) acțiunilor utilizatorului.
 * CERINȚĂ: Scrierea într-un fișier CSV (Audit).
 */
public class ServiciuAudit {
    // 1. Se definește calea către fișier. Fișierul va apărea în folderul rădăcină al proiectului.
    private static final String FISIER_PATH = "audit.csv";

    // 2. Se definește formatul datei și orei (An-Lună-Zi Oră:Minut:Secundă)
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Metodă statică pentru a scrie o acțiune în fișier.
     * Fiind statică, poate fi apelată de oriunde fără a crea un obiect ServiciuAudit.
     * * @param numeActiune String reprezentând numele operațiunii efectuate (ex: "transfer_bancar")
     */
    public static void log(String numeActiune) {
        /*
         * 'try-with-resources' pentru a crea FileWriter.
         * Parametrul 'true' din constructorul FileWriter activează modul APPEND.
         * Asta înseamnă că noile log-uri vor fi adăugate la finalul fișierului,
         * fără a șterge datele existente.
         */
        try (FileWriter writer = new FileWriter(FISIER_PATH, true)) {

            // Preluăm data și ora curentă a sistemului
            String dataOra = LocalDateTime.now().format(FORMATTER);

            // Construim rândul de tip CSV (valori separate prin virgulă)
            // Format: actiune, data_ora
            writer.append(numeActiune)
                    .append(",")
                    .append(dataOra)
                    .append("\n"); // Adăugăm o linie nouă pentru următorul log

            // Nu este nevoie de writer.close(), try-with-resources îl închide automat.

        } catch (IOException e) {
            // Erorile de tip Input/Output (ex: fișierul este blocat de alt program)
            System.out.println("Eroare la scrierea în audit: " + e.getMessage());
        }
    }
}