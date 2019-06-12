package se.rifr.domain;

import se.rifr.support.StdIO;
import se.rifr.support.Str;

import java.util.Objects;

public abstract class Vehicle implements java.io.Serializable {

    public static enum Size {SMALL,MEDIUM,LARGE,HUGE};

    private String   barcode;
    private String   model;
    private String   colour;
    private int      noOfWheels;
    private int      noiseLevel; // 0-9, 9 highest
    private String   fuel;
    private Size     size;
    private Customer customer;

    public Vehicle(Builder builder) {

        this.barcode    = Objects.requireNonNull(builder.barcode);
        this.customer   = Objects.requireNonNull(builder.customer);
        this.model      = builder.model;
        this.colour     = builder.colour;
        this.noOfWheels = builder.noOfWheels;
        this.noiseLevel = builder.noiseLevel;
        this.fuel       = builder.fuel;
        this.size       = builder.size;

    }

//    public Vehicle(String barcode,String model, String colour, Customer customer) {
//
//        this.barcode    = barcode;
//        this.customer   = customer;
//        this.model      = model;
//        this.colour     = colour;
//
//        this.noOfWheels = 4;
//        this.noiseLevel = 5;
//        this.fuel       = "PETROL";
//        this.size       = Size.MEDIUM;
//    }
//    public Vehicle(String barcode, String model, String colour, int noOfWheels, int noiseLevel, String fuel, Size size, Customer customer) {
//
//        this.barcode = barcode;
//        this.model = model;
//        this.colour = colour;
//        this.noOfWheels = noOfWheels;
//        this.noiseLevel = noiseLevel;
//        this.fuel = fuel;
//        this.size = size;
//        this.customer = customer;
//    }

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

    public static Builder builder(){ return new Builder();}

    public static class Builder <T> {

        private String   barcode;
        private String   model = "UNDEFINED";
        private String   colour= "UNDEFINED";
        private int      noOfWheels = 4;
        private int      noiseLevel = 5; // 0-9, 9 highest
        private String   fuel= "UNDEFINED";
        private Size     size = Size.MEDIUM;
        private Customer customer;

        public T withBarcode(String barcode){
            this.barcode = barcode;
            return (T)this;
        }

        public T withModel(String model){
            this.model = model;
            return (T)this;
        }

        public T withColour(String barcolourcode){
            this.colour = colour;
            return (T)this;
        }

        public T withNoOfWheels(int noOfWheels){
            this.noOfWheels = noOfWheels;
            return (T)this;
        }

        public T withNoiseLevel(int noiseLevel){
            this.noiseLevel = noiseLevel;
            return (T)this;
        }

        public T withFuel(String fuel){
            this.fuel = fuel;
            return (T)this;
        }

        public T withSize(Size size){
            this.size = size;
            return (T)this;
        }

        public T withCustomer(Customer customer){
            this.customer = customer;
            return (T)this;
        }

        //public Vehicle build(){
        //    return new Vehicle(this);
        //}
    }
}
