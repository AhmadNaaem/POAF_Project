import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class ExCSV {
    private static final String FILE_NAME = "cashbook.csv";

    public static void exportToCSV(List<Entry> entries) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            pw.println("Date,Type,Particulars,SalesDisc,PurchDisc,Cash,Bank");
            for (Entry e : entries) {
                pw.printf("%s,%s,%s,%.2f,%.2f,%.2f,%.2f%n",
                        e.date, e.type, e.particulars.replace(",", " "), e.salesDiscount, e.purchaseDiscount, e.cash, e.bank);
            }
            System.out.println("Exported to " + FILE_NAME);
        } catch (IOException ex) {
            System.out.println("Error exporting to CSV: " + ex.getMessage());
        }
    }

    public static void loadEntries(List<Entry> entries) {
        entries.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 7) continue;
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