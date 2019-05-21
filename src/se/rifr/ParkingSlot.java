package se.rifr;

public class ParkingSlot {

    Garage  garage;
    Floor   floor;
    int     placeNo;
    Vehicle reservedFor;
    Vehicle parked;

    public ParkingSlot(Garage garage, Floor floor, int placeNo) {
        this.garage = garage;
        this.floor = floor;
        this.placeNo = placeNo;
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

    public Vehicle getReservedFor() {
        return reservedFor;
    }

    public void setReservedFor(Vehicle reservedFor) {
        this.reservedFor = reservedFor;
    }

    public Vehicle getParked() {
        return parked;
    }

    public void setParked(Vehicle parked) {
        this.parked = parked;
    }
}
