import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashbookApp {
    private CashbookGUI cashbookGUI;

    public CashbookApp() {
        cashbookGUI = new CashbookGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CashbookApp app = new CashbookApp();
            app.cashbookGUI.setVisible(true);
        });
    }
}