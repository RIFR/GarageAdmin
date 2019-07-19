package se.rifr.domain;

import se.rifr.support.StdIO;
import se.rifr.support.Str;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

//@Entity
//@Table(name = "Scannings")
public class Scannings implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

//    @Column(name = "vehicle",nullable = false)
    private Vehicle vehicle;

//    @Column(name = "time",nullable = false)
    private LocalDateTime time;

//    @Column(name = "entered",nullable = false)
    private boolean entered;  // Enter or leaving

//    @Column(name = "garage",nullable = false)
    private Garage garage;

    public Scannings(Vehicle vehicle, LocalDateTime time, boolean entered, Garage garage) {
        this.vehicle = Objects.requireNonNull(vehicle);
        this.time = Objects.requireNonNull(time);
        this.entered = Objects.requireNonNull(entered);
        this.garage = Objects.requireNonNull(garage);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public boolean isEntered() {
        return entered;
    }

    public Garage getGarage() { return garage; }

    @Override
    public String toString() {
        return Str.padRight(vehicle.getBarcode(),10) +
              time.toLocalTime().toString().substring(0,19) + " " +
                (entered ? "ENTERED   ":"LEAVING " + " " +
                 Str.padRight(getGarage().getName(),30));
    }

    public static String toStringHeader() {
        String returnString;
        returnString  = Str.padRight("Reg No",16);
        returnString += Str.padRight("ENTER/LEAVE",16);
        returnString += Str.padRight("Garage",30);
        returnString += Str.padRight("Time",30);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getVehicle().getBarcode(),16);
        returnString += Str.padRight((isEntered() ? "ENTERED":"LEAVING"),16);
        returnString += Str.padRight(getGarage().getName(),30);
        returnString += Str.padRight(getTime().toString().substring(0,19),30);
        return returnString;
    }

}
