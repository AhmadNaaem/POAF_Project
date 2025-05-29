import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CashbookGUI extends JFrame {
    private JTextField serialNoField;
    private JTextField dateField;
    private JTextField particularsField;
    private JTextField ledgerFolioField;
    private JTextField salesDiscountField;
    private JTextField purchaseDiscountField;
    private JTextField cashField;
    private JTextField bankField;
    private JComboBox<String> typeComboBox;
    private JTextArea outputArea;
    private List<Entry> entries;

    public CashbookGUI() {
        entries = new ArrayList<>();
        setTitle("Cashbook Application");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));

        add(new JLabel("Type (receipt/payment):"));
        typeComboBox = new JComboBox<>(new String[]{"receipt", "payment"});
        add(typeComboBox);

        add(new JLabel("Serial No:"));
        serialNoField = new JTextField();
        add(serialNoField);

        add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        add(dateField);

        add(new JLabel("Particulars:"));
        particularsField = new JTextField();
        add(particularsField);

        add(new JLabel("Ledger Folio (LF):"));
        ledgerFolioField = new JTextField();
        add(ledgerFolioField);

        add(new JLabel("Sales Discount:"));
        salesDiscountField = new JTextField();
        add(salesDiscountField);

        add(new JLabel("Purchase Discount:"));
        purchaseDiscountField = new JTextField();
        add(purchaseDiscountField);

        add(new JLabel("Cash:"));
        cashField = new JTextField();
        add(cashField);

        add(new JLabel("Bank:"));
        bankField = new JTextField();
        add(bankField);

        JButton addButton = new JButton("Add Entry");
        addButton.addActionListener(new AddEntryAction());
        add(addButton);

        JButton exportButton = new JButton("Export to CSV");
        exportButton.addActionListener(new ExportCSVAction());
        add(exportButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        setVisible(true);
    }

    private class AddEntryAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String type = (String) typeComboBox.getSelectedItem();
                int serialNo = Integer.parseInt(serialNoField.getText());
                LocalDate date = LocalDate.parse(dateField.getText());
                String particulars = particularsField.getText();
                String ledgerFolio = ledgerFolioField.getText();
                double salesDiscount = type.equals("receipt") ? Double.parseDouble(salesDiscountField.getText()) : 0;
                double purchaseDiscount = type.equals("payment") ? Double.parseDouble(purchaseDiscountField.getText()) : 0;
                double cash = Double.parseDouble(cashField.getText());
                double bank = Double.parseDouble(bankField.getText());

                Entry entry = new Entry(serialNo, date, particulars, ledgerFolio, salesDiscount, purchaseDiscount, cash, bank, type);
                entries.add(entry);
                outputArea.append("Entry added: " + entry.toCSV() + "\n");
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(CashbookGUI.this, "Error adding entry: " + ex.getMessage());
            }
        }
    }

private class ExportCSVAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(CashbookGUI.this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            CSVUtils.exportEntriesToCSV(entries, filePath);
            JOptionPane.showMessageDialog(CashbookGUI.this, "CSV exported to: " + filePath);
        }
    }
}
    private void clearFields() {
        serialNoField.setText("");
        dateField.setText("");
        particularsField.setText("");
        ledgerFolioField.setText("");
        salesDiscountField.setText("");
        purchaseDiscountField.setText("");
        cashField.setText("");
        bankField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CashbookGUI::new);
    }
}