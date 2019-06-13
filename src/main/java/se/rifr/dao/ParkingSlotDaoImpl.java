package se.rifr.dao;

import se.rifr.support.FileIO;
import se.rifr.domain.ParkingSlot;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingSlotDaoImpl implements ParkingSlotDao{

    String fileName;

    private Map<String,ParkingSlot> ParkingSlots = new HashMap<>();

    @Override
    public void maintain(ParkingSlot ParkingSlot) {
        ParkingSlots.put(ParkingSlot.getKey(),ParkingSlot);
        FileIO.writeObject(ParkingSlots, fileName);
    }

    @Override
    public void delete(ParkingSlot ParkingSlot) {
        ParkingSlots.remove(ParkingSlot);
        FileIO.writeObject(ParkingSlots, fileName);
    }

    @Override
    public Optional<ParkingSlot> read(String key) {
        return Optional.ofNullable(ParkingSlots.get(key));
    }

    @Override
    public Collection<ParkingSlot> readAllParkingSlots() {
        return  ParkingSlots.values().stream().collect(Collectors.toSet());
    }

    @Override
    public Collection<ParkingSlot> readAllFreeParkingSlots() {
        return  ParkingSlots.values().stream().filter(item -> item.isFree()).collect(Collectors.toSet());
    }

    @Override
    public Collection<ParkingSlot> readParkingSlots(String inGarage) {
        return  ParkingSlots.values().stream()
                .filter(item -> item.getGarage().getName().equals(inGarage))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<ParkingSlot> readFreeParkingSlots(String inGarage) {
        return  ParkingSlots.values().stream()
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
            ParkingSlots = tempList;
    };

    @Override
    public void stop(){
        if (ParkingSlots != null) FileIO.writeObject(ParkingSlots, fileName);
    };

}
