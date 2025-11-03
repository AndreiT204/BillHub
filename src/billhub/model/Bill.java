package billhub.model;

public class Bill {
    private final String id;
    private final String providerName;
    private final double amount;
    private final String dueDate;
    private boolean paid;

    public Bill(String id, String providerName, double amount, String dueDate, boolean paid) {
        this.id = id;
        this.providerName = providerName;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paid = paid;
    }
    public String getId() {return id;}
    public String getProviderName() {return providerName;}
    public double getAmount() {return amount;}
    public String getDueDate() {return dueDate;}
    public boolean isPaid() {return paid;}
    public void markAsPaid() {this.paid = true;}

    @Override public String toString() {
        return String.format("%-4s | %-10s | %8.2f | %s | %s", id, providerName, amount, dueDate, paid ? "Paid" : "Unpaid");
    }       //%-x left align an x-wide string  %x.2f right align a floating point no. with 2 decimals

    public String toCsv() {
        return String.join(",", id, providerName, String.valueOf(amount), dueDate, String.valueOf(paid));
    }
}

