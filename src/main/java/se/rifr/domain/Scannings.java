package se.rifr.domain;

import se.rifr.support.StdIO;
import se.rifr.support.Str;

import java.time.LocalDateTime;

public class Scannings implements java.io.Serializable{

    private Vehicle vehicle;
    private LocalDateTime time;
    private boolean entered;  // Enter or leaving
    private Garage garage;

    public Scannings(Vehicle vehicle, LocalDateTime time, boolean entered, Garage garage) {
        this.vehicle = vehicle;
        this.time = time;
        this.entered = entered;
        this.garage = garage;
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
