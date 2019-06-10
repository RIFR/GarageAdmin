package se.rifr.dao;

import se.rifr.domain.Garage;

import java.util.Collection;
import java.util.Optional;

public interface GarageDao {

    void maintain(Garage Garage);
    void delete(Garage Garage);

    Optional<Garage> read(String key);

    Collection<Garage> readAllGarages();

    void printOut();
    void printOut(Collection<Garage> GarageCollection);

    void start(String fileName);
    void stop();
}
