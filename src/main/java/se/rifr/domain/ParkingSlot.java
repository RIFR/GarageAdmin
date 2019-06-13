package se.rifr.domain;

import se.rifr.support.StdIO;
import se.rifr.support.Str;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlot implements  Comparable<ParkingSlot>, java.io.Serializable {

    private Garage garage;
    private Floor floor;
    private int           placeNo;
    private Vehicle.Size size;      // size of the slot
    private double        feePerHour = 0.0d;
    private double        feePerMonth = 0.0d;
    private Vehicle       parked = null;
    private LocalDateTime timeParked;

    public ParkingSlot(ParkingSlot.Builder builder) {

        this.garage     = Objects.requireNonNull(builder.garage);
        this.floor      = Objects.requireNonNull(builder.floor);
        this.placeNo    = Objects.requireNonNull(builder.placeNo);
        this.size       = Objects.requireNonNull(builder.size);
        if (builder.fee > 200)
            this.feePerMonth = builder.fee;
        else
            this.feePerHour  = builder.fee;
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

    public double getFeePerMonth() {
        return feePerMonth;
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
        if (getFeePerMonth() == 0.0d)
            returnString += ", Fee/Hour=='" +  Str.padRight(Double.toString(getFeePerHour()),10);
        else
            returnString += ", Fee/Month'" +  Str.padRight(Double.toString(getFeePerMonth()),10);
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
        returnString += Str.padRight("Fee(H/M)",10);
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
        if (getFeePerMonth() == 0.0d)
            returnString += Str.padLeft(Double.toString(getFeePerHour()) + "  ",10);
        else
            returnString += Str.padLeft(Double.toString(getFeePerMonth()) + "  ",10);

        if (this.parked != null) {
            returnString += Str.padRight(getParked().getBarcode(),20);
            returnString += Str.padRight(getTimeParked().toString().substring(0,19),20);
            returnString += Str.padLeft(Long.toString(Math.abs(Duration.between(LocalDateTime.now(), getTimeParked()).toMinutes())) + "   ",10);
            returnString += Str.padRight(getParked().getCustomer().getTelephoneNumber(),20);
        }

        return returnString;
    }

    public static Builder builder(){ return new Builder();}

    public static class Builder {

        private Garage        garage;
        private Floor         floor;
        private Integer       placeNo;
        private Vehicle.Size  size;      // size of the slot
        private Double        fee = 0.0d;

        public Builder withGarage(Garage garage){
            this.garage = garage;
            return this;
        }

        public Builder withFloor(Floor floor){
            this.floor = floor;
            return this;
        }

        public Builder withPlaceNo(int placeNo){
            this.placeNo = placeNo;
            return this;
        }

        public Builder withSize(Vehicle.Size size){
            this.size = size;
            return this;
        }

        public Builder withFee(double fee){
            this.fee = fee;
            return this;
        }

        public ParkingSlot build(){
            return new ParkingSlot(this);
        }

    }

}
