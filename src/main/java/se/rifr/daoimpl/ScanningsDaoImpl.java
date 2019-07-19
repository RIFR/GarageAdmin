package se.rifr.daoimpl;

import se.rifr.dao.ScanningsDao;
import se.rifr.support.FileIO;
import se.rifr.domain.Scannings;

import java.util.*;
import java.util.stream.Collectors;

public class ScanningsDaoImpl implements ScanningsDao {

    String fileName;

    private List<Scannings> scannings = new ArrayList<>();

    @Override
    public void add(Scannings Scanning) {
        scannings.add(Scanning);
        if (fileName != null)
            FileIO.writeObject(scannings, fileName);
    }

    @Override
    public Collection<Scannings> readAllScannings() {
        return scannings.stream().collect(Collectors.toSet());
    }

    @Override
    public Collection<Scannings> readVehicleScannings(String barcode) {
        return scannings.stream()
                .filter(item -> item.getVehicle().getBarcode().equals(barcode))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Scannings> readCustomerScannings(String barcode) {
        return scannings.stream()
                .filter(item -> item.getVehicle().getCustomer().getBarCode().equals(barcode))
                .collect(Collectors.toSet());
    }

    @Override
    public void printOut(){
        printOut(readAllScannings());
    };

    @Override
    public void printOut(Collection<Scannings> collection){
        System.out.println(Scannings.toStringHeader());
        if (collection != null)
            collection.stream()
                    .sorted(Comparator.comparing(item -> item.getTime()))
                    .sorted(Comparator.comparing(item -> item.getVehicle().getBarcode()))
                    .sorted(Comparator.comparing(item -> item.getGarage().getName()))
                    .forEach(item -> System.out.println(item.toStringLine()));
    };

    @Override
    public void start(String fileName){
        this.fileName = fileName;

        List<Scannings> tempList = FileIO.readObject(fileName);
        if (tempList != null)
            scannings = tempList;
    };

    @Override
    public void stop(){
        if (scannings != null && fileName != null) {
            FileIO.writeObject(scannings, fileName);
            fileName = null;
        }
    }

}
