package se.rifr;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class ParkingSlot implements  Comparable<ParkingSlot>, java.io.Serializable {

    private Garage        garage;
    private Floor         floor;
    private int           placeNo;
    private Vehicle.Size  size;      // size of the slot
    private double        feePerHour = 0.0d;
    private Vehicle       parked = null;
    private LocalDateTime timeParked;

    public ParkingSlot(Garage garage, Floor floor, int placeNo, Vehicle.Size size, double feePerHour) {
        this.garage = garage;
        this.floor = floor;
        this.placeNo = placeNo;
        this.size = size;
        this.feePerHour = feePerHour;
    }

    public String getKey() {
        return garage.getName()+"-"+floor.getLevel()+"-"+placeNo;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public int getPlaceNo() {
        return placeNo;
    }

    public void setPlaceNo(int placeNo) {
        this.placeNo = placeNo;
    }

    public Vehicle getParked() {
        return parked;
    }

    public void setParked(Vehicle parked) {
        this.parked     = parked;
        this.timeParked = LocalDateTime.now();
    }

    public Vehicle.Size getSize() {
        return size;
    }

    public void setSize(Vehicle.Size size) {
        this.size = size;
    }

    public double getFeePerHour() {
        return feePerHour;
    }

    public double getFeePerMinute() {
        return feePerHour / 60.0d;
    }

    public void setFeePerHour(double feePerHour) {
        this.feePerHour = feePerHour;
    }

    public LocalDateTime getTimeParked() {
        return timeParked;
    }

    public long free() {
        this.parked = null;
        Duration duration = Duration.between(LocalDateTime.now(), this.timeParked);
        return Math.abs(duration.toMinutes());
    }

    public boolean isFree() {
        return (this.parked == null);
    }

    @Override
    public int compareTo(ParkingSlot o) {
        return garage.getName().compareTo(o.getGarage().getName());
    }
    @Override
    public String toString() {
        String returnString;
        returnString = "Garage{Name=" + Str.padRight(getGarage().getName(),30);
        returnString += ", Floor='" +  Str.padRight(Integer.toString(getFloor().getLevel()),10);
        returnString += ", Index='" +  Str.padRight(Integer.toString(getPlaceNo()),10);
        returnString += ", Size='" +  Str.padRight(getSize().toString(),10);
        returnString += ", Fee/Hour='" +  Str.padRight(Double.toString(getFeePerHour()),10);
        if (this.parked != null) {
            returnString += ", Vehicle='" +  Str.padRight(getParked().getBarcode(),20);
            returnString += ", At Time='" +  Str.padRight(getTimeParked().toString().substring(0,19),20);
            returnString += ", Minutes='" +  Str.padRight(Long.toString(Math.abs(Duration.between(LocalDateTime.now(), getTimeParked()).toMinutes())),10);
        }
        returnString += '}';
        return returnString;
    }

    public static String toStringHeader() {
        String returnString;
        returnString  = Str.padRight("Name",30);
        returnString += Str.padRight("Floor",10);
        returnString += Str.padRight("Index",10);
        returnString += Str.padRight("Size",10);
        returnString += Str.padRight("Fee/Hour",10);
        returnString += Str.padRight("Parked",20);
        returnString += Str.padRight("Parked at time",20);
        returnString += Str.padRight("Minutes",10);
        returnString += Str.padRight("Telephone Number",20);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getGarage().getName(),30);
        returnString += Str.padRight(Integer.toString(getFloor().getLevel()),10);
        returnString += Str.padRight(Integer.toString(getPlaceNo()),10);
        returnString += Str.padRight(getSize().toString(),10);
        returnString += Str.padLeft(Double.toString(getFeePerHour()) + "  ",10);
        if (this.parked != null) {
            returnString += Str.padRight(getParked().getBarcode(),20);
            returnString += Str.padRight(getTimeParked().toString().substring(0,19),20);
            returnString += Str.padLeft(Long.toString(Math.abs(Duration.between(LocalDateTime.now(), getTimeParked()).toMinutes())) + "   ",10);
            returnString += Str.padRight(getParked().getCustomer().getTelephoneNumber(),20);
        }

        return returnString;
    }

}
