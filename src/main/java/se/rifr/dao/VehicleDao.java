package se.rifr.dao;

import se.rifr.domain.Vehicle;

import java.util.Collection;
import java.util.Optional;

public interface VehicleDao {

    void maintain(Vehicle Vehicle);
    void delete(Vehicle Vehicle);

    Optional<Vehicle> read(String key);

    Collection<Vehicle> readAllVehicles();

    void printOut();
    void printOut(Collection<Vehicle> collection);

    void start(String fileName);
    void stop();
}
