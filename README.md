# SistemBancar
proiect PAOJ

Acesta este un proiect de tip consolă (CLI) dezvoltat în Java, creat pentru disciplina **Programare Avansată pe Obiecte (PAO)**. Aplicația simulează logica de business a unei bănci, gestionând relațiile dintre clienți, conturile acestora și tranzacțiile financiare.

Proiectul demonstrează stăpânirea conceptelor fundamentale de **Programare Orientată pe Obiecte (OOP)**, **Structuri de Date (Collections Framework)**, **I/O (Gestiunea Fișierelor)** și **Persistența Datelor (JDBC & PostgreSQL)**.


## Tehnologii Utilizate
* **Limbaj:** Java (JDK 8+)
* **Bază de Date:** PostgreSQL
* **Conectivitate:** JDBC (Java Database Connectivity)



## Cerințe

Aplicația a fost construită pentru a bifa:

1. **Principiile OOP (Încapsulare, Moștenire, Agregare)**
   * Folosirea modificatorilor `private` și `protected` pentru ascunderea datelor.
   * **Moștenire (IS-A):** Clasa `Client` extinde superclasa `Persoana`.
   * **Agregare (HAS-A):** Clasa `Client` deține o listă de conturi (`List<Cont>`).
   * Modelele de date sunt construite sub formă de clase **POJO (Entity / DTO)**.
2. **Colecții și Sortare**
   * Utilizarea a cel puțin două colecții: `ArrayList` (pentru conturi) și **`TreeSet`** (pentru stocarea clienților în memorie).
   * Obiectele de tip `Client` sunt sortate automat alfabetic, prin implementarea interfeței **`Comparable`** (rescrierea metodei `compareTo`).
3. **Persistența Datelor și Operații CRUD**
   * Bază de date relațională cu implementarea pattern-ului **Singleton** pentru gestionarea eficientă a conexiunii (`ConfigurareBazaDate`).
   * Implementarea completă a operațiilor **CRUD** (Create, Read, Update, Delete). Operația de *Update* este demonstrată prin mecanismul de transfer bancar, care actualizează soldurile conturilor.
   * **Integritate Referențială:** Ștergerea în cascadă a clienților și a conturilor asociate pentru a evita conturile „orfane”.
4. **Securitate și Robustețe**
   * Folosirea exclusivă a **`PreparedStatement`** pentru interogările SQL, prevenind vulnerabilitățile de tip *SQL Injection*.
   * Sistem de autentificare robust, cu prelucrarea datelor de intrare (`.trim()`, `.equalsIgnoreCase()`).
5. **Sistem de Audit (Fișiere I/O)**
   * Jurnalizarea fiecărei acțiuni din sistem într-un fișier `audit.csv`.
   * Folosirea `FileWriter` în mod *append* (prin blocuri *try-with-resources*) pentru a înregistra structura: `denumire_actiune, data_si_ora`.


## Arhitectura Aplicației

Proiectul respectă **Principiul Responsabilității Unice (SRP)** și este structurat pe 3 niveluri logice:
* **Modele (Domain Layer):** `Persoana`, `Client`, `Cont`, `Tranzactie` (Clase de date / POJO).
* **Servicii (Business & Persistence Layer):** `ServiciuBancar` (gestionează logica SQL/CRUD), `ServiciuAudit` (gestionează scrierea în fișiere CSV).
* **Controler / UI:** `Main` (gestionează exclusiv interacțiunea cu utilizatorul și meniul text).


## Structura Proiectului

ProiectBancaraPAO/
 |-- src/
 |   |-- ConfigurareBazaDate.java  # Configurarea conexiunii JDBC (Singleton)
 |   |-- Main.java                 # Punctul de intrare (Meniul text și Autentificare)
 |   |-- ServiciuAudit.java        # Logica de scriere în fișier CSV (I/O)
 |   |-- ServiciuBancar.java       # Logica de business și operații SQL (CRUD)
 |   |-- Persoana.java             # Superclasa de bază
 |   |-- Client.java               # Subclasă care extinde Persoana
 |   |-- Cont.java                 # Entitate / POJO pentru contul bancar
 |   |-- Tranzactie.java           # Obiect de transfer de date (DTO)
 |-- bancapao2026.sql              # Backup-ul bazei de date PostgreSQL
 |-- audit.csv                     # Fișierul generat automat cu istoricul acțiunilor
 |-- README.md                     # Documentația proiectului
