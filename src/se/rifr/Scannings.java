package se.rifr;

import java.time.LocalDateTime;

public class Scannings implements java.io.Serializable{

    private Vehicle vehicle;
    private LocalDateTime time;
    private boolean entered;  // entered or withdraw

    public Scannings(Vehicle vehicle, LocalDateTime time, boolean entered) {
        this.vehicle = vehicle;
        this.time    = time;
        this.entered = entered;
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

    @Override
    public String toString() {
        return Str.padRight(vehicle.getBarcode(),30) +
              time.toLocalTime().toString().substring(0,8) + " " +
                (entered ? "ENTERED   ":"LEAVING ");
    }

    public static String toStringHeader() {
        String returnString;
        returnString  = Str.padRight("Reg No",16);
         returnString += Str.padRight("Time",16);
        returnString += Str.padRight("Direction",14);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getVehicle().getBarcode(),16);
        returnString += Str.padRight(getTime().toString().substring(0,19),16);
        returnString += Str.padRight((isEntered() ? "ENTERED":"LEAVING"),14);
        return returnString;
    }

}
