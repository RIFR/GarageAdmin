package se.rifr.dao;

import se.rifr.support.FileIO;
import se.rifr.domain.ParkingSlot;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingSlotDaoImpl implements ParkingSlotDao{

    String fileName;

    private Map<String,ParkingSlot> parkingSlots = new HashMap<>();

    @Override
    public void maintain(ParkingSlot ParkingSlot) {
        parkingSlots.put(ParkingSlot.getKey(),ParkingSlot);
        if (fileName != null)
            FileIO.writeObject(parkingSlots, fileName);
    }

    @Override
    public void delete(ParkingSlot ParkingSlot) {
        boolean ok = parkingSlots.remove(ParkingSlot.getKey(),ParkingSlot);
        if (ok && fileName != null)
            FileIO.writeObject(parkingSlots, fileName);
    }

    @Override
    public Optional<ParkingSlot> read(String key) {
        return Optional.ofNullable(parkingSlots.get(key));
    }

    @Override
    public Collection<ParkingSlot> readAllParkingSlots() {
        return  parkingSlots.values().stream().collect(Collectors.toSet());
    }

    @Override
    public Collection<ParkingSlot> readAllFreeParkingSlots() {
        return  parkingSlots.values().stream().filter(item -> item.isFree()).collect(Collectors.toSet());
    }

    @Override
    public Collection<ParkingSlot> readParkingSlots(String inGarage) {
        return  parkingSlots.values().stream()
                .filter(item -> item.getGarage().getName().equals(inGarage))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<ParkingSlot> readFreeParkingSlots(String inGarage) {
        return  parkingSlots.values().stream()
                .filter(item -> item.getGarage().getName().equals(inGarage))
                .filter(item -> item.isFree())
                .collect(Collectors.toSet());
    }

    @Override
    public void printOut(){
        printOut(readAllParkingSlots());
    };

    @Override
    public void printOut(Collection<ParkingSlot> collection){
        System.out.println(ParkingSlot.toStringHeader());
        if (collection != null)
            collection.stream()
                .sorted(Comparator.comparing(item -> item.getPlaceNo()))
                .sorted(Comparator.comparing(item -> item.getFloor().getLevel()))
                .sorted(Comparator.comparing(item -> item.getGarage().getName()))
                .forEach(item -> System.out.println(item.toStringLine()));
    };

    @Override
    public void start(String fileName){
        this.fileName = fileName;

        Map<String, ParkingSlot> tempList = FileIO.readObject(fileName);
        if (tempList != null)
            parkingSlots = tempList;
    };

    @Override
    public void stop(){
        if (parkingSlots != null && fileName != null) {
            FileIO.writeObject(parkingSlots, fileName);
            fileName = null;
        }
    }

}
