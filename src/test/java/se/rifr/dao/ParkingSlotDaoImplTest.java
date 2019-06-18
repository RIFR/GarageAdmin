package se.rifr.dao;

import org.junit.Test;
import se.rifr.domain.*;
import se.rifr.domain.vehicles.Car;
import se.rifr.domain.vehicles.Mc;

import java.util.Optional;

import static org.junit.Assert.*;

public class ParkingSlotDaoImplTest {

    Customer testCustomer = new Customer.Builder()
            .withFirstName("Kalle")
            .withLastName("Anka")
            .withBarCode("001")
            .withemail("kalle.anka@ankeborg.org")
            .withTelephoneNumber("001")
            .withUserName("KAAN")
            .build();

    Vehicle testVehicle1 = new Mc.Builder()
            .withBarcode("AAA000")
            .withColour("BLACK")
            .withCustomer(testCustomer)
            .withFuel("GASOLINE")
            .withModel("SUZUKI")
            .withNoiseLevel(5)
            .withNoOfWheels(2)
            .withSize(Vehicle.Size.SMALL)
            .build();

    Vehicle testVehicle2 = new Car.Builder()
            .withBarcode("BBB000")
            .withColour("BLUE")
            .withCustomer(testCustomer)
            .withFuel("DIESEL")
            .withModel("VOLVO")
            .withNoiseLevel(3)
            .withNoOfWheels(4)
            .withSize(Vehicle.Size.MEDIUM)
            .build();

    Garage testGarage = new Garage("HUS NUMMER 1","No 1");
    Floor testFloor   = new Floor(1,"On number 1");

    @Test
    public void startstop() {

        ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();
        parkingSlotDao.start("src/test/files/parkingslottest.ser");

        ParkingSlot parkingSlot = new ParkingSlot.Builder()
            .withGarage(testGarage)
            .withFloor(testFloor)
            .withPlaceNo(1)
            .withSize(Vehicle.Size.SMALL)
            .withFee(5.0d)
            .build();

        parkingSlotDao.maintain(parkingSlot);

        parkingSlotDao.stop();

        parkingSlotDao.delete(parkingSlot);

        parkingSlotDao.start("src/test/files/parkingslottest.ser");

        Optional<ParkingSlot> parkingSlotOpt = parkingSlotDao.read(parkingSlot.getKey());

        assertTrue (parkingSlotOpt.isPresent());

        parkingSlotDao.stop();

    }

    @Test
    public void maintain() {
        ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

        ParkingSlot parkingSlot = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(1)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build();

        parkingSlotDao.maintain(parkingSlot);

        Optional <ParkingSlot> parkingSlotOpt = parkingSlotDao.read(parkingSlot.getKey());

        assertTrue (parkingSlotOpt.isPresent());

        assertEquals(parkingSlot,parkingSlotOpt.get());

    }

    @Test
    public void delete() {
        ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

        ParkingSlot parkingSlot = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(1)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build();

        parkingSlotDao.maintain(parkingSlot);

        Optional <ParkingSlot> parkingSlotOpt = parkingSlotDao.read(parkingSlot.getKey());

        assertTrue (parkingSlotOpt.isPresent());

        parkingSlotDao.delete(parkingSlotOpt.get());

        parkingSlotOpt = parkingSlotDao.read(parkingSlot.getKey());

        assertFalse (parkingSlotOpt.isPresent());

    }

    @Test
    public void read() {
        ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

        ParkingSlot parkingSlot1 = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(1)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build();

        parkingSlotDao.maintain(parkingSlot1);

        ParkingSlot parkingSlot2 = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(2)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build();

        parkingSlotDao.maintain(parkingSlot2);

        Optional <ParkingSlot> parkingSlotOpt = parkingSlotDao.read(parkingSlot1.getKey());

        assertTrue (parkingSlotOpt.isPresent());

        parkingSlotOpt = parkingSlotDao.read(parkingSlot1.getKey());

        assertTrue (parkingSlotOpt.isPresent());

        parkingSlotOpt = parkingSlotDao.read("99999");

        assertFalse (parkingSlotOpt.isPresent());
    }

    @Test
    public void readAllParkingSlots() {
        ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

        parkingSlotDao.maintain(new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(1)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build());

        parkingSlotDao.maintain(new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(2)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build());

        parkingSlotDao.maintain(new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(3)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build());

        assertTrue (parkingSlotDao.readAllParkingSlots().size()==3);
    }

    @Test
    public void printOut() {

        ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

        parkingSlotDao.maintain(new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(1)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build());

        parkingSlotDao.maintain(new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(2)
                .withSize(Vehicle.Size.MEDIUM)
                .withFee(10.0d)
                .build());

        parkingSlotDao.maintain(new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(3)
                .withSize(Vehicle.Size.LARGE)
                .withFee(15.0d)
                .build());

        parkingSlotDao.maintain(new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(4)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build());

        parkingSlotDao.maintain(new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(5)
                .withSize(Vehicle.Size.HUGE)
                .withFee(25.0d)
                .build());

        parkingSlotDao.printOut();

    }

    @Test
    public void readAllFreeParkingSlots() {
        ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

        ParkingSlot parkingSlot1 = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(1)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build();

        parkingSlot1.setParked(testVehicle1);

        parkingSlotDao.maintain(parkingSlot1);

        ParkingSlot parkingSlot2 = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(2)
                .withSize(Vehicle.Size.MEDIUM)
                .withFee(10.0d)
                .build();

        parkingSlot2.setParked(testVehicle2);

        parkingSlotDao.maintain(parkingSlot2);

        ParkingSlot parkingSlot3 = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(3)
                .withSize(Vehicle.Size.MEDIUM)
                .withFee(10.0d)
                .build();

        parkingSlotDao.maintain(parkingSlot3);

        assertTrue (parkingSlotDao.readAllFreeParkingSlots().size()==1);
    }

    @Test
    public void readParkingSlots() {
        ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

        ParkingSlot parkingSlot1 = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(1)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build();

        parkingSlot1.setParked(testVehicle1);

        parkingSlotDao.maintain(parkingSlot1);

        ParkingSlot parkingSlot2 = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(2)
                .withSize(Vehicle.Size.MEDIUM)
                .withFee(10.0d)
                .build();

        parkingSlot2.setParked(testVehicle2);

        parkingSlotDao.maintain(parkingSlot2);

        ParkingSlot parkingSlot3 = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(3)
                .withSize(Vehicle.Size.MEDIUM)
                .withFee(10.0d)
                .build();

        parkingSlotDao.maintain(parkingSlot3);

        assertTrue (parkingSlotDao.readParkingSlots(testGarage.getKey()).size()==3);
        assertTrue (parkingSlotDao.readParkingSlots("ANNAT HUS").isEmpty());
    }

    @Test
    public void readFreeParkingSlots() {
        ParkingSlotDao parkingSlotDao = new ParkingSlotDaoImpl();

        ParkingSlot parkingSlot1 = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(1)
                .withSize(Vehicle.Size.SMALL)
                .withFee(5.0d)
                .build();

        parkingSlot1.setParked(testVehicle1);

        parkingSlotDao.maintain(parkingSlot1);

        ParkingSlot parkingSlot2 = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(2)
                .withSize(Vehicle.Size.MEDIUM)
                .withFee(10.0d)
                .build();

        parkingSlot2.setParked(testVehicle2);

        parkingSlotDao.maintain(parkingSlot2);

        ParkingSlot parkingSlot3 = new ParkingSlot.Builder()
                .withGarage(testGarage)
                .withFloor(testFloor)
                .withPlaceNo(3)
                .withSize(Vehicle.Size.MEDIUM)
                .withFee(10.0d)
                .build();

        parkingSlotDao.maintain(parkingSlot3);

        assertTrue (parkingSlotDao.readFreeParkingSlots(testGarage.getKey()).size()==1);
        assertTrue (parkingSlotDao.readFreeParkingSlots("ANNAT HUS").isEmpty());
    }

}
