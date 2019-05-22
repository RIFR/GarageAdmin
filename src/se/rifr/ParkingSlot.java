package se.rifr;

public class ParkingSlot implements java.io.Serializable {

    Garage       garage;
    Floor        floor;
    int          placeNo;
    Vehicle.Size size;
    Vehicle      parked = null;

    public ParkingSlot(Garage garage, Floor floor, int placeNo, Vehicle.Size size) {
        this.garage = garage;
        this.floor = floor;
        this.placeNo = placeNo;
        this.size = size;
    }

    public String getKey() {
        return garage.name+"-"+floor.level+"-"+placeNo;
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
        this.parked = parked;
    }

    public Vehicle.Size getSize() {
        return size;
    }

    public void setSize(Vehicle.Size size) {
        this.size = size;
    }

    public void free() {
        this.parked = null;
    }

    public boolean isFree() {
        return (this.parked == null);
    }

    @Override
    public String toString() {
        String returnString;
        returnString = "Garage{Name=" + Str.padRight(getGarage().getName(),30);
        returnString += ", Floor='" +  Str.padRight(Integer.toString(getFloor().getLevel()),5);
        returnString += ", Index='" +  Str.padRight(Integer.toString(getPlaceNo()),5);
        if (this.parked != null)
            returnString += ", Vehicle='" +  Str.padRight(getParked().getBarcode(),20);
        returnString += '}';
        return returnString;
    }

    public static String toStringHeader() {
        String returnString;
        returnString  = Str.padRight("Name",30);
        returnString += Str.padRight("Floor",5);
        returnString += Str.padRight("Index",5);
        returnString += Str.padRight("Parked",20);
        returnString += "\r\n" + StdIO.ConsoleColors.BLUE + Str.pad('-',140)+ StdIO.ConsoleColors.RESET;
        return returnString;
    }

    public String toStringLine() {
        String returnString;
        returnString  = Str.padRight(getGarage().getName(),30);
        returnString += Str.padRight(Integer.toString(getFloor().getLevel()),5);
        returnString += Str.padRight(Integer.toString(getPlaceNo()),5);
        if (this.parked != null)
            returnString += Str.padRight(getParked().getBarcode(),20);

        return returnString;
    }

}
