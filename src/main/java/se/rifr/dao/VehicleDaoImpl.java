package se.rifr.dao;

import se.rifr.support.FileIO;
import se.rifr.domain.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

public class VehicleDaoImpl implements VehicleDao{

    String fileName;

    private Map<String,Vehicle> vehicles = new HashMap<>();

    @Override
    public void maintain(Vehicle Vehicle) {
        vehicles.put(Vehicle.getKey(),Vehicle);
        if (fileName != null)
            FileIO.writeObject(vehicles, fileName);
    }

    @Override
    public void delete(Vehicle Vehicle) {
        boolean ok = vehicles.remove(Vehicle.getKey(),Vehicle);
        if (ok && fileName != null)
            FileIO.writeObject(vehicles, fileName);
    }

    @Override
    public Optional<Vehicle> read(String key) {
        return Optional.ofNullable(vehicles.get(key));
    }

    @Override
    public Collection<Vehicle> readAllVehicles() {
        return vehicles.values().stream().collect(Collectors.toSet());
    }

    @Override
    public void printOut(){
        printOut(readAllVehicles());
    };

    @Override
    public void printOut(Collection<Vehicle> collection){
        System.out.println(Vehicle.toStringHeader());
        if (collection != null)
            collection.stream()
                    .sorted(Comparator.comparing(item -> item.getBarcode()))
                    .sorted(Comparator.comparing(item -> item.getClass().getSimpleName()))
                    .sorted(Comparator.comparing(item -> item.getSize()))
                    .sorted(Comparator.comparing(item -> item.getCustomer().getFullName()))
                    .forEach(item -> System.out.println(item.toStringLine()));
    };

    @Override
    public void start(String fileName){
        this.fileName = fileName;

        Map<String, Vehicle> tempList = FileIO.readObject(fileName);
        if (tempList != null)
            vehicles = tempList;
    };

    @Override
    public void stop(){
        if (vehicles != null && fileName != null) {
            FileIO.writeObject(vehicles, fileName);
            fileName = null;
        }
    }

}
