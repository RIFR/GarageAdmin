package se.rifr.domain.vehicles;

import se.rifr.domain.Vehicle;

public class Truck extends Vehicle {

    public Truck (Truck.Builder builder) {
        super(builder);
    }

    public static class Builder extends Vehicle.Builder <Truck.Builder> {
        public Truck build(){
            return new Truck(this);
        }
    }

}
