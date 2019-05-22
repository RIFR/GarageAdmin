package se.rifr;

public class Vehicle implements Comparable<Vehicle>, java.io.Serializable {

    public static enum Size {SMALL,MEDIUM,LARGE,HUGE};

    String           barcode;
    //String           model;
    //String           colour;
    //int              noOfWheels;
    //int              noiseLevel; // 0-9, 9 highest
    //String           fuel;
    Size             size;
    //boolean          ReportedStolen;
    Customer         customer;

    public Vehicle(String barcode, Size needSize, Customer customer) {
        this.barcode  = barcode;
        this.size     = needSize;
        this.customer = customer;
    }

    public String getKey() { return barcode; }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Size getSize() {
        return size;
    }

    public void seSize(Size size) {
        this.size = size;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Vehicle{" + Str.padRight(barcode.trim(),10) +
                "Need Size=" + Str.padLeft(size.toString(),10) +
                //"Colour=" + Str.padLeft(colour.trim(),20) +
                //", NoOfWheels=" + Str.padLeft(Integer.toString(noOfWheels),10) +
                //", NoiseLevel=" + Str.padLeft(Integer.toString(noiseLevel),10) +
                //", Fuel=" + Str.padLeft(fuel,10) +
                ", Customer=" + Str.padLeft(customer.getFullName(),30) +
                '}';
    }

    public static String toStringHeader() {
        String returnString;
        returnString = Str.padRight("Barcode",10);
        returnString += Str.padRight("Need size",10);
        //returnString += Str.padRight("Colour",20);
        //returnString += Str.padRight("No Of Wheels",10);
        //returnString += Str.padRight("Noise Level",10);
        //returnString += Str.padRight("Fuel",10);
        returnString += Str.padRight("Name",30);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getBarcode(),10);
        returnString += Str.padRight(getSize().toString(),10);
        //returnString += Str.padRight(getColour(),20);
        //returnString += Str.padRight(Integer.toString(getNoOfWheels()),10);
        //returnString += Str.padRight(Integer.toString(getNoiseLevel()),10);
        //returnString += Str.padRight(getFuel(),10);
        if (getCustomer()!= null)
            returnString += Str.padRight(getCustomer().getFullName(),30);
        return returnString;
    }

    @Override
    public int compareTo(Vehicle o) { return size.compareTo(o.size);
    }
}

class Mc extends Vehicle {

    public Mc(String registrationNumber, Customer customer) {
        super(registrationNumber, Size.SMALL, customer);
    }
}

class Car extends Vehicle {

    public Car(String registrationNumber, Customer customer) {
        super(registrationNumber, Size.MEDIUM, customer);
    }
}

class Truck extends Vehicle {

    public Truck(String registrationNumber, Customer customer) {
        super(registrationNumber, Size.LARGE, customer);
    }
}

class ArticulatedLorry extends Vehicle {

    int noOfBeds;

    public ArticulatedLorry(String registrationNumber, Customer customer,int noOfBeds) {
        super(registrationNumber, Size.HUGE, customer);
        this.noOfBeds = noOfBeds;
    }

    public int getNoOfBeds() { return noOfBeds; }
}


