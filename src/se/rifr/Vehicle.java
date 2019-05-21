package se.rifr;

public class Vehicle {

    String   registrationNumber;
    String   model;
    String   colour;
    int      noOfWheels;
    int      noiseLevel; // 0-9, 9 highest
    String   fuel;
    boolean  ReportedStolen;
    Customer customer;

    public Vehicle(String registrationNumber, String model, String colour, int noOfWheels, int noiseLevel, String fuel, Customer customer) {
        this.registrationNumber = registrationNumber;
        this.model              = model;
        this.colour             = colour;
        this.noOfWheels         = noOfWheels;
        this.noiseLevel         = noiseLevel;
        this.fuel               = fuel;
        this.customer           = customer;

        this.ReportedStolen = false;
    }

    public String getKey() {
        return registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getNoOfWheels() {
        return noOfWheels;
    }

    public void setNoOfWheels(int noOfWheels) {
        this.noOfWheels = noOfWheels;
    }

    public int getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(int noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isReportedStolen() {
        return ReportedStolen;
    }

    public void setReportedStolen(boolean reportedStolen) {
        ReportedStolen = reportedStolen;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    @Override
    public String toString() {
        return "Vehicle{" + Str.padRight(registrationNumber.trim(),10) +
                "Colour=" + Str.padLeft(colour.trim(),20) +
                ", NoOfWheels=" + Str.padLeft(Integer.toString(noOfWheels),10) +
                ", NoiseLevel=" + Str.padLeft(Integer.toString(noiseLevel),10) +
                ", Fuel=" + Str.padLeft(fuel,10) +
                ", Customer=" + Str.padLeft(customer.getFullName(),30) +
                '}';
    }

    public static String toStringHeader() {
        String returnString;
        returnString = Str.padRight("Reg No",10);
        returnString += Str.padRight("Colour",20);
        returnString += Str.padRight("No Of Wheels",10);
        returnString += Str.padRight("Noise Level",10);
        returnString += Str.padRight("Fuel",10);
        returnString += Str.padRight("Name",30);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getRegistrationNumber(),10);
        returnString += Str.padRight(getColour(),20);
        returnString += Str.padRight(Integer.toString(getNoOfWheels()),10);
        returnString += Str.padRight(Integer.toString(getNoiseLevel()),10);
        returnString += Str.padRight(getFuel(),10);
        returnString += Str.padRight(getCustomer().getFullName(),30);
        return returnString;
    }

}
