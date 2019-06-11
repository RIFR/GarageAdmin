package se.rifr.dao;

import se.rifr.domain.ParkingSlot;

import java.util.Collection;
import java.util.Optional;

public interface ParkingSlotDao {

    void maintain(ParkingSlot ParkingSlot);
    void delete(ParkingSlot ParkingSlot);

    Optional<ParkingSlot> read(String key);

    Collection<ParkingSlot> readAllParkingSlots();
    Collection<ParkingSlot> readAllFreeParkingSlots();
    Collection<ParkingSlot> readParkingSlots(String inGarage);
    Collection<ParkingSlot> readFreeParkingSlots(String inGarage);

    void printOut();
    void printOut(Collection<ParkingSlot> ParkingSlotCollection);

    void start(String fileName);
    void stop();
}
