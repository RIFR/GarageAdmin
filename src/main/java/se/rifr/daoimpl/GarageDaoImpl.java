package se.rifr.daoimpl;

import se.rifr.dao.GarageDao;
import se.rifr.support.FileIO;
import se.rifr.domain.Garage;

import java.util.*;
import java.util.stream.Collectors;

public class GarageDaoImpl implements GarageDao {

    String fileName;

    private Map<String,Garage> garages = new HashMap<>();

    @Override
    public void maintain(Garage garage) {
        garages.put(garage.getKey(),garage);
        if (fileName != null)
        FileIO.writeObject(garages, fileName);
    }

    @Override
    public void delete(Garage garage) {
        boolean ok = garages.remove(garage.getKey(),garage);
        if (ok && fileName != null)
            FileIO.writeObject(garages, fileName);
    }

    @Override
    public Optional<Garage> read(String key) {
        return Optional.ofNullable(garages.get(key));
    }

    @Override
    public Collection<Garage> readAllGarages() {
        return garages.values().stream().collect(Collectors.toSet());
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
            garages = tempList;
    };

    @Override
    public void stop(){
        if (garages != null && fileName != null) {
            FileIO.writeObject(garages, fileName);
            fileName = null;
        }
    }

}
