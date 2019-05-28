package se.rifr;

public class Vehicle implements java.io.Serializable { // Comparable<Vehicle>, java.io.Serializable {

    public static enum Size {SMALL,MEDIUM,LARGE,HUGE};

    private String   barcode;
    private String   model;
    private String   colour;
    private int      noOfWheels;
    private int      noiseLevel; // 0-9, 9 highest
    private String   fuel;
    private Size     size;
    private Customer customer;

    public Vehicle(String barcode,String model, String colour, Customer customer) {

        this.barcode    = barcode;
        this.customer   = customer;
        this.model      = model;
        this.colour     = colour;

        this.noOfWheels = 4;
        this.noiseLevel = 5;
        this.fuel       = "PETROL";
        this.size       = Size.MEDIUM;
    }
    public Vehicle(String barcode, String model, String colour, int noOfWheels, int noiseLevel, String fuel, Size size, Customer customer) {

        this.barcode = barcode;
        this.model = model;
        this.colour = colour;
        this.noOfWheels = noOfWheels;
        this.noiseLevel = noiseLevel;
        this.fuel = fuel;
        this.size = size;
        this.customer = customer;
    }

    public String getKey() { return barcode; }

    public String getBarcode() {
        return barcode;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    @Override
    public String toString() {
        return "Vehicle{" + Str.padRight(barcode.trim(),10) +
                ",Size=" + Str.padLeft(size.toString(),10) +
                ",Colour=" + Str.padLeft(colour.trim(),20) +
                ",NoOfWheels=" + Str.padLeft(Integer.toString(noOfWheels),10) +
                ",NoiseLevel=" + Str.padLeft(Integer.toString(noiseLevel),10) +
                ",Fuel=" + Str.padLeft(fuel,10) +
                ",Customer=" + Str.padLeft(customer.getFullName(),30) +
                '}';
    }

    public static String toStringHeader() {
        String returnString;
        returnString = Str.padRight("Barcode",16);
        returnString += Str.padRight("Class",16);
        returnString += Str.padRight("Colour",14);
        returnString += Str.padRight("NoOfWheels",20);
        returnString += Str.padRight("NoiseLevel",20);
        returnString += Str.padRight("Fuel",20);
        returnString += Str.padRight("Size",20);
        returnString += Str.padRight("Name",30);
         returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getBarcode(),16);
        returnString += Str.padRight(getClass().getSimpleName().toUpperCase(),16);
        returnString += Str.padRight(getColour(),14);
        returnString += Str.padRight(Integer.toString(getNoOfWheels()),20);
        returnString += Str.padRight(Integer.toString(getNoiseLevel()),20);
        returnString += Str.padRight(getFuel().toUpperCase(),20);
        returnString += Str.padRight(getSize().toString(),20);
        if (getCustomer()!= null)
            returnString += Str.padRight(getCustomer().getFullName(),30);
        return returnString;
    }

    //@Override
    // public int compareTo(Vehicle o) { return size.compareTo(o.size);
    //}
}

class Mc extends Vehicle {

    public Mc(String barcode, String model, String colour,Customer customer) {
        super(barcode, model, colour, 2, 7, "PETROL", Size.SMALL, customer);
    }

    public Mc(String barcode, String model, String colour, int noOfWheels, int noiseLevel, String fuel, Customer customer) {
        super(barcode, model, colour, noOfWheels, noiseLevel, fuel, Size.SMALL, customer);
    }
}

class Car extends Vehicle {

    public Car(String barcode, String model, String colour,Customer customer) {
        super(barcode, model, colour, 4, 3, "PETROL", Size.MEDIUM, customer);
    }
    public Car(String barcode, String model, String colour, int noOfWheels, int noiseLevel, String fuel, Customer customer) {
        super(barcode, model, colour, noOfWheels, noiseLevel, fuel, Size.MEDIUM, customer);
    }
}

class Truck extends Vehicle {

    public Truck(String barcode, String model, String colour, Customer customer) {
        super(barcode, model, colour, 4, 6, "DIESEL", Size.LARGE, customer);
    }
    public Truck(String barcode, String model, String colour, int noOfWheels, int noiseLevel, String fuel, Customer customer) {
        super(barcode, model, colour, noOfWheels, noiseLevel, fuel, Size.LARGE, customer);
    }
}
class Lorry extends Vehicle {

    public Lorry(String barcode, String model, String colour, Customer customer) {
        super(barcode, model, colour, 4, 7, "DIESEL", Size.LARGE, customer);
    }
    public Lorry(String barcode, String model, String colour, int noOfWheels, int noiseLevel, String fuel, Customer customer) {
        super(barcode, model, colour, noOfWheels, noiseLevel, fuel, Size.LARGE, customer);
    }
}

class Bus extends Vehicle {

    int noOfSeats;

    public Bus(String barcode, String model, String colour, Customer customer, int noOfSeats) {
        super(barcode, model, colour, 8, 7, "DIESEL", Size.HUGE, customer);
        this.noOfSeats = noOfSeats;
    }

    public Bus(String barcode, String model, String colour, int noOfWheels, int noiseLevel, String fuel, Customer customer, int noOfSeats) {
        super(barcode, model, colour, noOfWheels, noiseLevel, fuel, Size.HUGE, customer);
        this.noOfSeats = noOfSeats;
    }

    public int getNoOfSeats() { return noOfSeats; }

    public void setNoOfSeats(int noOfSeats) { this.noOfSeats = noOfSeats; }
}
//Långtradare
class Juggernaut extends Vehicle {

    int noOfBeds;

    public Juggernaut(String barcode, String model, String colour,Customer customer, int noOfBeds) {
        super(barcode, model, colour, 20, 6, "DIESEL", Size.HUGE, customer);
        this.noOfBeds = noOfBeds;
    }

    public Juggernaut(String barcode, String model, String colour, int noOfWheels, int noiseLevel, String fuel, Customer customer, int noOfBeds) {
        super(barcode, model, colour, noOfWheels, noiseLevel, fuel, Size.HUGE, customer);
        this.noOfBeds = noOfBeds;
    }

    public int getNoOfBeds() { return noOfBeds; }

    public void setNoOfBeds(int noOfBeds) { this.noOfBeds = noOfBeds; }
}


