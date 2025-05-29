import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    public static void exportEntriesToCSV(List<Entry> entries, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("Receipt");
            writer.println("Date,S.No,Particulars,LF,Sales Discount,Cash,Bank");
            for (Entry e : entries) {
                if ("receipt".equalsIgnoreCase(e.type)) {
                    writer.println(e.toCSV());
                }
            }
            writer.println();
            writer.println("Payment");
            writer.println("Date,S.No,Particulars,Purchase Discount,Cash,Bank");
            for (Entry e : entries) {
                if ("payment".equalsIgnoreCase(e.type)) {
                    writer.println(e.toCSV());
                }
            }
            System.out.println("CSV exported as '" + filePath + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Entry> importEntriesFromCSV(String filePath) {
        List<Entry> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip section headers and column headers
                if (line.trim().equalsIgnoreCase("Receipt") ||
                    line.trim().equalsIgnoreCase("Payment") ||
                    line.trim().startsWith("Date,")) {
                    continue;
                }
                if (!line.trim().isEmpty()) {
                    entries.add(Entry.fromCSV(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
