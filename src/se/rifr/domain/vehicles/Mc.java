package se.rifr.domain.vehicles;

import se.rifr.domain.Vehicle;

public class Mc extends Vehicle {

    public Mc (Mc.Builder builder) {
        super(builder);
    }

    public static class Builder extends Vehicle.Builder <Mc.Builder> {
        public Mc build(){
            return new Mc(this);
        }
    }

}
