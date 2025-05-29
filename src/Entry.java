class Entry {
    int serialNo;
    LocalDate date;
    String particulars;
    String ledgerFolio; // LF
    double salesDiscount;     // only for receipt
    double purchaseDiscount;  // only for payment
    double cash;
    double bank;
    String type; // "receipt" or "payment"

    public Entry(int serialNo, LocalDate date, String particulars, String ledgerFolio,
        double salesDiscount, double purchaseDiscount, double cash, double bank, String type) {
        this.serialNo = serialNo;
        this.date = date;
        this.particulars = particulars;
        this.ledgerFolio = ledgerFolio;
        this.salesDiscount = salesDiscount;
        this.purchaseDiscount = purchaseDiscount;
        this.cash = cash;
        this.bank = bank;
        this.type = type;
    }

    public String toCSV() {
        if ("receipt".equalsIgnoreCase(type)) {
            return String.format("%s,%d,%s,%s,%.2f,%.2f,%.2f",
                    date, serialNo, particulars, ledgerFolio, salesDiscount, cash, bank);
        } else {
            return String.format("%s,%d,%s,%.2f,%.2f,%.2f",
                    date, serialNo, particulars, purchaseDiscount, cash, bank);
        }
    }

    public static Entry fromCSV(String line) {
        String[] parts = line.split(",");
        String type = parts.length == 7 ? "receipt" : "payment";
        int serialNo = Integer.parseInt(parts[1]);
        LocalDate date = LocalDate.parse(parts[0]);
        String particulars = parts[2];
        if ("receipt".equalsIgnoreCase(type)) {
            String lf = parts[3];
            double sd = Double.parseDouble(parts[4]);
            double cash = Double.parseDouble(parts[5]);
            double bank = Double.parseDouble(parts[6]);
            return new Entry(serialNo, date, particulars, lf, sd, 0, cash, bank, type);
        } else {
            double pd = Double.parseDouble(parts[3]);
            double cash = Double.parseDouble(parts[4]);
            double bank = Double.parseDouble(parts[5]);
            return new Entry(serialNo, date, particulars, "", 0, pd, cash, bank, type);
        }
    }
}