package se.rifr;

public class Account implements java.io.Serializable{

    private Customer customer;
    private String bankId;
    private double saldo;
    private String description;
    private long   parkedTime; // minutes since last payment

    public Account(Customer customer, String bankId, double saldo, String description) {
        this.customer    = customer;
        this.bankId      = bankId;
        this.saldo       = saldo;
        this.description = description;
        this.parkedTime  = 0;
    }

    public String getKey() { return customer.getBarCode(); }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void changeSaldo(double amount, boolean deposit ) {
        saldo += (deposit ? amount : -amount);
    }

    public long getParkedTime() {
        return parkedTime;
    }

    public void setParkedTime(long parkedTime) {
        this.parkedTime = parkedTime;
    }

    public void updateParkedTime(long parkedTime) {
        this.parkedTime += parkedTime;
    }

    public void clearParkedTime() {
        this.parkedTime = 0;
    }

    @Override
    public String toString() {
        return "Account{" + Str.padRight(customer.getFullName(),30) +
                "id=" + Str.padLeft(bankId.trim(),20) +
                ", saldo=" + Str.padLeft(Double.toString(saldo).trim(),10) +
                ", parked=" + Str.padLeft(Long.toString(parkedTime).trim(),10) +
                ", description='" + description + '\'' +
                '}';
    }

    public static String toStringHeader() {
        String returnString;
        returnString  = Str.padRight("Firstname",16);
        returnString += Str.padRight("Lastname",16);
        returnString += Str.padRight("Barcode",14);
        returnString += Str.padRight("Account No",40);
        returnString += Str.padRight("Saldo",20);
        returnString += Str.padRight("Parked Time (min)",20);
        returnString += Str.padRight("Description",20);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(customer.getFirstName(),16);
        returnString += Str.padRight(customer.getLastName(),16);
        returnString += Str.padRight(customer.getBarCode(),14);
        returnString += Str.padRight(getBankId(),40);
        returnString += Str.padRight(Double.toString(getSaldo()),20);
        returnString += Str.padLeft(Long.toString(getParkedTime()) +"   ",20);
        returnString += Str.padRight(getDescription(),20);
        return returnString;
    }

}
