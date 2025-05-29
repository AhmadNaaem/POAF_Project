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
            System.out.println("\n1. Add Receipt\n2. Add Payment\n3. View Cashbook\n4. Export to CSV\n5. GUI\n6. Exit");
            System.out.print("Choose option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1": addEntry("receipt"); break;
                case "2": addEntry("payment"); break;
                case "3": viewCashbook(); break;
                case "4": ExCSV.exportToCSV(entries); break;
                case "5": CashbookGUI.main(null); break;
                case "6": return;
                default: System.out.println("Invalid option.");
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
            } catch (DateTimeParseException ignored) {}
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