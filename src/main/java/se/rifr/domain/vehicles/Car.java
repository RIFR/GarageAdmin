package se.rifr.domain.vehicles;

import se.rifr.domain.Vehicle;

public class Car extends Vehicle {

    public static Builder builder(){ return new Builder();}

    public Car (Car.Builder builder) {
        super(builder);
    }

    public static class Builder extends Vehicle.Builder  <Car.Builder> {
        public Car build(){
            return new Car(this);
        }
    }

}