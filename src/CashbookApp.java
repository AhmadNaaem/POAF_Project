import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class CashbookApp {
    static final Scanner scanner = new Scanner(System.in);
    static final List<Entry> entries = new ArrayList<>();
    private static final DateTimeFormatter[] DATE_FORMATS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd MMM yyyy"),
            DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
    };

    public static void main(String[] args) {
        ExCSV.loadEntries(entries);
        while (true) {
            System.out.println("\n1. Add Receipt\n2. Add Payment\n3. View Cashbook\n4. Export to CSV\n5. Exit");
            System.out.print("Choose option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    addEntry("receipt");
                    break;
                case "2":
                    addEntry("payment");
                    break;
                case "3":
                    viewCashbook();
                    break;
                case "4":
                    ExCSV.exportToCSV(entries);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    static void addEntry(String type) {
        try {
            System.out.print("Enter date (e.g., 23-May-2025 or 23/05/2025): ");
            String dateStr = scanner.nextLine();
            LocalDate date = parseFlexibleDate(dateStr);

            System.out.print("Enter particulars: ");
            String particulars = scanner.nextLine();

            double salesDiscount = 0, purchaseDiscount = 0, cash, bank;

            if (type.equals("receipt")) {
                salesDiscount = getDouble("Enter sales discount (0 if none): ");
            } else {
                purchaseDiscount = getDouble("Enter purchase discount (0 if none): ");
            }

            cash = getDouble("Enter cash amount: ");
            bank = getDouble("Enter bank amount: ");

            Entry e = new Entry(date, particulars, salesDiscount, purchaseDiscount, cash, bank, type);
            entries.add(e);
            System.out.println("Entry added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static double getDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    public static LocalDate parseFlexibleDate(String dateStr) {
        for (DateTimeFormatter fmt : DATE_FORMATS) {
            try {
                return LocalDate.parse(dateStr, fmt);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new IllegalArgumentException("Date could not be parsed. Please try a different format.");
    }

    static void viewCashbook() {
        System.out.printf("%n%-12s %-10s %-15s %-10s %-10s %-10s %-10s%n",
                "Date", "Type", "Particulars", "SalesDisc", "PurchDisc", "Cash", "Bank");
        for (Entry e : entries) {
            System.out.printf("%-12s %-10s %-15s %-10.2f %-10.2f %-10.2f %-10.2f%n",
                    e.date, e.type, e.particulars, e.salesDiscount, e.purchaseDiscount, e.cash, e.bank);
        }
    }
}

// Entry class
class Entry {
    public LocalDate date;
    public String particulars;
    public double salesDiscount;
    public double purchaseDiscount;
    public double cash;
    public double bank;
    public String type;

    public Entry(LocalDate date, String particulars, double salesDiscount, double purchaseDiscount, double cash,
            double bank, String type) {
        this.date = date;
        this.particulars = particulars;
        this.salesDiscount = salesDiscount;
        this.purchaseDiscount = purchaseDiscount;
        this.cash = cash;
        this.bank = bank;
        this.type = type;
    }
}

// ExCSV class
class ExCSV {
    private static final String FILE_NAME = "cashbook.csv";

    public static void exportToCSV(List<Entry> entries) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            pw.println("Date,Type,Particulars,SalesDisc,PurchDisc,Cash,Bank");
            for (Entry e : entries) {
                pw.printf("%s,%s,%s,%.2f,%.2f,%.2f,%.2f%n",
                        e.date, e.type, e.particulars.replace(",", " "), e.salesDiscount, e.purchaseDiscount, e.cash,
                        e.bank);
            }
            System.out.println("Exported to " + FILE_NAME);
        } catch (IOException ex) {
            System.out.println("Error exporting to CSV: " + ex.getMessage());
        }
    }

    public static void loadEntries(List<Entry> entries) {
        entries.clear();
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 7)
                    continue;
                LocalDate date = LocalDate.parse(parts[0]);
                String type = parts[1];
                String particulars = parts[2];
                double salesDisc = Double.parseDouble(parts[3]);
                double purchDisc = Double.parseDouble(parts[4]);
                double cash = Double.parseDouble(parts[5]);
                double bank = Double.parseDouble(parts[6]);
                entries.add(new Entry(date, particulars, salesDisc, purchDisc, cash, bank, type));
            }
        } catch (Exception ex) {
            System.out.println("Error loading CSV: " + ex.getMessage());
        }
    }
}
