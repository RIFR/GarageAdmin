package se.rifr.dao;

import se.rifr.domain.Scannings;

import java.util.Collection;
import java.util.Optional;

public interface ScanningsDao {

    void add(Scannings Scanning);

    Collection<Scannings> readAllScannings();
    Collection<Scannings> readVehicleScannings(String barcode);
    Collection<Scannings> readCustomerScannings(String barcode);

    void printOut();
    void printOut(Collection<Scannings> collection);

    void start(String fileName);
    void stop();
}
