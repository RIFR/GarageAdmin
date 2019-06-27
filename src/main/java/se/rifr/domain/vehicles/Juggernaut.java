package se.rifr.domain.vehicles;

import se.rifr.domain.Vehicle;

public //Långtradare
class Juggernaut extends Vehicle {

    int noOfBeds;

    public int getNoOfBeds() { return noOfBeds; }

    public void setNoOfBeds(int noOfBeds) { this.noOfBeds = noOfBeds; }

    public Juggernaut (Juggernaut.Builder builder) {
        super(builder);
        this.noOfBeds = builder.noOfBeds;
    }
    public static class Builder extends Vehicle.Builder <Juggernaut.Builder> {

        private int noOfBeds;

        public Builder withNoOfBeds(int noOfBeds){
            this.noOfBeds = noOfBeds;
            return this;
        }

        public Juggernaut build(){
            return new Juggernaut(this);
        }
    }

}

