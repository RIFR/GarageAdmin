package se.rifr.domain.vehicles;

import se.rifr.domain.Vehicle;

public class Bus extends Vehicle {

    int noOfSeats;

    public int getNoOfSeats() { return noOfSeats; }

    public void setNoOfSeats(int noOfSeats) { this.noOfSeats = noOfSeats; }

    public static Builder builder(){ return new Builder();}

    public Bus (Bus.Builder builder) {
        super(builder);
        this.noOfSeats = builder.noOfSeats;
    }
    public static class Builder extends Vehicle.Builder  <Bus.Builder>{

        private int noOfSeats;

        public Builder withNoOfSeats(int noOfSeats){
            this.noOfSeats = noOfSeats;
            return this;
        }

        public Bus build(){
            return new Bus(this);
        }
    }
}
