package se.rifr.dao;

import se.rifr.support.FileIO;
import se.rifr.domain.Garage;

import java.util.*;
import java.util.stream.Collectors;

public class GarageDaoImpl implements GarageDao{

    String fileName;

    private Map<String,Garage> Garages = new HashMap<>();

    @Override
    public void maintain(Garage garage) {
        Garages.put(garage.getKey(),garage);
        FileIO.writeObject(Garages, fileName);
    }

    @Override
    public void delete(Garage garage) {
        Garages.remove(garage);
        FileIO.writeObject(Garages, fileName);
    }

    @Override
    public Optional<Garage> read(String key) {
        return Optional.ofNullable(Garages.get(key));
    }

    @Override
    public Collection<Garage> readAllGarages() {
        return Garages.values().stream().collect(Collectors.toSet());
    }

    @Override
    public void printOut(){
        printOut(readAllGarages());
    };

    @Override
    public void printOut(Collection<Garage> collection){
        System.out.println(Garage.toStringHeader());
        if (collection != null)
            collection.stream()
                .sorted(Comparator.comparing(item -> item.getName()))
                .forEach(item -> System.out.println(item.toStringLine()));
    };

    @Override
    public void start(String fileName){
        this.fileName = fileName;

        Map<String, Garage> tempList = FileIO.readObject(fileName);
        if (tempList != null)
            Garages = tempList;
    };

    @Override
    public void stop(){
        if (Garages != null) FileIO.writeObject(Garages, fileName);
    };

}
