package se.rifr.dao;

import se.rifr.support.FileIO;
import se.rifr.domain.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

public class VehicleDaoImpl implements VehicleDao{

    String fileName;

    private Map<String,Vehicle> Vehicles = new HashMap<>();

    @Override
    public void maintain(Vehicle Vehicle) {
        Vehicles.put(Vehicle.getKey(),Vehicle);
        FileIO.writeObject(Vehicles, fileName);
    }

    @Override
    public void delete(Vehicle Vehicle) {
        Vehicles.remove(Vehicle);
        FileIO.writeObject(Vehicles, fileName);
    }

    @Override
    public Optional<Vehicle> read(String key) {
        return Optional.ofNullable(Vehicles.get(key));
    }

    @Override
    public Collection<Vehicle> readAllVehicles() {
        return Vehicles.values().stream().collect(Collectors.toSet());
    }

    @Override
    public void printOut(){
        printOut(readAllVehicles());
    };

    @Override
    public void printOut(Collection<Vehicle> VehicleCollection){
        System.out.println(Vehicle.toStringHeader());
        VehicleCollection.stream()
                .sorted(Comparator.comparing(item -> item.getSize()))
                .sorted(Comparator.comparing(item -> item.getBarcode()))
                .sorted(Comparator.comparing(item -> item.getCustomer().getFullName()))
                .forEach(item -> System.out.println(item.toStringLine()));
    };

    @Override
    public void start(String fileName){
        this.fileName = fileName;

        Map<String, Vehicle> tempList = FileIO.readObject(fileName);
        if (tempList != null)
            Vehicles = tempList;
    };

    @Override
    public void stop(){
        if (Vehicles != null) FileIO.writeObject(Vehicles, fileName);
    };

}
