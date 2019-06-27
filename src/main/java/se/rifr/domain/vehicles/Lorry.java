package se.rifr.domain.vehicles;

import se.rifr.domain.Vehicle;

public class Lorry extends Vehicle {

    public Lorry (Lorry.Builder builder) {
        super(builder);
    }

    public static class Builder extends Vehicle.Builder <Lorry.Builder> {
        public Lorry build(){
            return new Lorry(this);
        }
    }
}
