import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CashbookGUI extends JFrame {
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final List<Entry> entries;

    public CashbookGUI(List<Entry> entries) {
        this.entries = entries;

        setTitle("Cashbook Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        String[] columns = { "Date", "Type", "Particulars", "SalesDisc", "PurchDisc", "Cash", "Bank" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton addReceiptBtn = new JButton("Add Receipt");
        JButton addPaymentBtn = new JButton("Add Payment");
        JButton exportBtn = new JButton("Export to CSV");
        JButton refreshBtn = new JButton("Refresh");

        addReceiptBtn.addActionListener(e -> showEntryDialog("receipt"));
        addPaymentBtn.addActionListener(e -> showEntryDialog("payment"));
        exportBtn.addActionListener(e -> ExCSV.exportToCSV(entries));
        refreshBtn.addActionListener(e -> refreshTable());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addReceiptBtn);
        buttonPanel.add(addPaymentBtn);
        buttonPanel.add(exportBtn);
        buttonPanel.add(refreshBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void showEntryDialog(String type) {
        JTextField dateField = new JTextField();
        JTextField particularsField = new JTextField();
        JTextField salesDiscField = new JTextField("0");
        JTextField purchDiscField = new JTextField("0");
        JTextField cashField = new JTextField();
        JTextField bankField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Date (yyyy-MM-dd):"));
        panel.add(dateField);
        panel.add(new JLabel("Particulars:"));
        panel.add(particularsField);

        if (type.equals("receipt")) {
            panel.add(new JLabel("Sales Discount:"));
            panel.add(salesDiscField);
        } else {
            panel.add(new JLabel("Purchase Discount:"));
            panel.add(purchDiscField);
        }

        panel.add(new JLabel("Cash:"));
        panel.add(cashField);
        panel.add(new JLabel("Bank:"));
        panel.add(bankField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add " + capitalize(type),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                LocalDate date = CashbookApp.parseFlexibleDate(dateField.getText().trim());
                String particulars = particularsField.getText().trim();
                double salesDiscount = type.equals("receipt") ? parseOrZero(salesDiscField.getText()) : 0.0;
                double purchaseDiscount = type.equals("payment") ? parseOrZero(purchDiscField.getText()) : 0.0;
                double cash = parseOrZero(cashField.getText());
                double bank = parseOrZero(bankField.getText());

                Entry entry = new Entry(date, particulars, salesDiscount, purchaseDiscount, cash, bank, type);
                entries.add(entry);
                refreshTable();

                JOptionPane.showMessageDialog(this, "Entry added successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Entry e : entries) {
            tableModel.addRow(new Object[] {
                    e.date, e.type, e.particulars, e.salesDiscount, e.purchaseDiscount, e.cash, e.bank
            });
        }
    }

    private double parseOrZero(String text) {
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static void main(String[] args) {
        ExCSV.loadEntries(CashbookApp.entries);
        SwingUtilities.invokeLater(() -> {
            CashbookGUI gui = new CashbookGUI(CashbookApp.entries);
            gui.setVisible(true);
        });
    }
}
