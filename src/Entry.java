import java.time.LocalDate;

public class Entry {
    public LocalDate date;
    public String particulars;
    public double salesDiscount;
    public double purchaseDiscount;
    public double cash;
    public double bank;
    public String type;

    public Entry(LocalDate date, String particulars, double salesDiscount, double purchaseDiscount, double cash, double bank, String type) {
        this.date = date;
        this.particulars = particulars;
        this.salesDiscount = salesDiscount;
        this.purchaseDiscount = purchaseDiscount;
        this.cash = cash;
        this.bank = bank;
        this.type = type;
    }
}
