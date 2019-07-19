package se.rifr.dao;

import org.junit.Test;
import se.rifr.daoimpl.ScanningsDaoImpl;
import se.rifr.domain.Customer;
import se.rifr.domain.Garage;
import se.rifr.domain.Scannings;
import se.rifr.domain.Vehicle;
import se.rifr.domain.vehicles.Mc;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ScanningsDaoImplTest {

    Customer testCustomer = new Customer.Builder()
            .withFirstName("Kalle")
            .withLastName("Anka")
            .withBarCode("001")
            .withemail("kalle.anka@ankeborg.org")
            .withTelephoneNumber("001")
            .withUserName("KAAN")
            .build();

    Vehicle testVehicle = new Mc.Builder()
            .withBarcode("AAA000")
            .withColour("BLACK")
            .withCustomer(testCustomer)
            .withFuel("GASOLINE")
            .withModel("SUZUKI")
            .withNoiseLevel(5)
            .withNoOfWheels(2)
            .withSize(Vehicle.Size.SMALL)
            .build();

    Garage testGarage = new Garage("HUS NUMMER 1","No 1");

    @Test
    public void startstop() {

        ScanningsDao scanningsDao = new ScanningsDaoImpl();
        scanningsDao.start("src/test/files/scanningstest.ser");

        int size = scanningsDao.readAllScannings().size();

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now(),true,testGarage));

        scanningsDao.stop();

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now(),false,testGarage));

        scanningsDao.start("src/test/files/scanningstest.ser");

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now(),false,testGarage));

        assertTrue (scanningsDao.readAllScannings().size()==size + 2);

        scanningsDao.stop();

    }

    @Test
    public void readAllScannings() {
        ScanningsDao scanningsDao = new ScanningsDaoImpl();

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now(),true,testGarage));

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(5),false,testGarage));

        assertTrue (scanningsDao.readAllScannings().size()==2);
    }

    @Test
    public void printOut() {

        ScanningsDao scanningsDao = new ScanningsDaoImpl();

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now(),true,testGarage));
        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(5),false,testGarage));
        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(10),true,testGarage));
        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(15),false,testGarage));

        scanningsDao.printOut();

    }    @Test
    public void add() {
        ScanningsDao scanningsDao = new ScanningsDaoImpl();

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now(),true,testGarage));

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(5),false,testGarage));

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(10),true,testGarage));

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(15),false,testGarage));

        assertTrue (scanningsDao.readAllScannings().size()==4);    }

    @Test
    public void readVehicleScannings() {
        ScanningsDao scanningsDao = new ScanningsDaoImpl();

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now(),true,testGarage));

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(5),false,testGarage));

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(10),true,testGarage));

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(15),false,testGarage));

        assertTrue (scanningsDao.readVehicleScannings("AAA000").size()==4);
        assertTrue (scanningsDao.readVehicleScannings("AAA001").isEmpty());
    }

    @Test
    public void readCustomerScannings() {
        ScanningsDao scanningsDao = new ScanningsDaoImpl();

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now(),true,testGarage));

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(5),false,testGarage));

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(10),true,testGarage));

        scanningsDao.add(new Scannings(testVehicle,LocalDateTime.now().plusMinutes(15),false,testGarage));

        assertTrue (scanningsDao.readCustomerScannings("001").size()==4);
        assertTrue (scanningsDao.readCustomerScannings("002").isEmpty());
    }


}
