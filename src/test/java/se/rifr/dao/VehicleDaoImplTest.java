package se.rifr.dao;

import org.junit.Test;
import se.rifr.domain.Customer;
import se.rifr.domain.Vehicle;
import se.rifr.domain.vehicles.*;

import java.util.Optional;

import static org.junit.Assert.*;

public class VehicleDaoImplTest {

    Customer testCustomer = new Customer.Builder()
            .withFirstName("Kalle")
            .withLastName("Anka")
            .withBarCode("001")
            .withemail("kalle.anka@ankeborg.org")
            .withTelephoneNumber("001")
            .withUserName("KAAN")
            .build();

    @Test
    public void startstop() {

        VehicleDao vehicleDao = new VehicleDaoImpl();
        vehicleDao.start("src/test/files/vehicletest.ser");

        Vehicle vehicle = new Mc.Builder()
                .withBarcode("AAA000")
                .withColour("BLACK")
                .withCustomer(testCustomer)
                .withFuel("GASOLINE")
                .withModel("SUZUKI")
                .withNoiseLevel(5)
                .withNoOfWheels(2)
                .withSize(Vehicle.Size.SMALL)
                .build();

        vehicleDao.maintain(vehicle);

        vehicleDao.stop();

        vehicleDao.delete(vehicle);

        vehicleDao.start("src/test/files/vehicletest.ser");

        Optional<Vehicle> vehicleOpt = vehicleDao.read(vehicle.getKey());

        assertTrue (vehicleOpt.isPresent());

        vehicleDao.stop();

    }

    @Test
    public void maintain() {
        VehicleDao vehicleDao = new VehicleDaoImpl();

        Vehicle vehicle = new Mc.Builder()
                .withBarcode("AAA000")
                .withColour("BLACK")
                .withCustomer(testCustomer)
                .withFuel("GASOLINE")
                .withModel("SUZUKI")
                .withNoiseLevel(5)
                .withNoOfWheels(2)
                .withSize(Vehicle.Size.SMALL)
                .build();

        vehicleDao.maintain(vehicle);

        Optional <Vehicle> vehicleOpt = vehicleDao.read(vehicle.getKey());

        assertTrue (vehicleOpt.isPresent());

        assertEquals(vehicle,vehicleOpt.get());

    }

    @Test
    public void delete() {
        VehicleDao vehicleDao = new VehicleDaoImpl();

        vehicleDao.maintain(new Mc.Builder()
                .withBarcode("AAA000")
                .withColour("BLACK")
                .withCustomer(testCustomer)
                .withFuel("GASOLINE")
                .withModel("SUZUKI")
                .withNoiseLevel(5)
                .withNoOfWheels(2)
                .withSize(Vehicle.Size.SMALL)
                .build());

        vehicleDao.maintain(new Car.Builder()
                .withBarcode("BBB000")
                .withColour("BLUE")
                .withCustomer(testCustomer)
                .withFuel("DIESEL")
                .withModel("VOLVO")
                .withNoiseLevel(3)
                .withNoOfWheels(4)
                .withSize(Vehicle.Size.MEDIUM)
                .build());

        Optional <Vehicle> vehicleOpt = vehicleDao.read("AAA000");

        assertTrue (vehicleOpt.isPresent());

        vehicleDao.delete(vehicleOpt.get());

        vehicleOpt = vehicleDao.read("AAA000");

        assertFalse (vehicleOpt.isPresent());

    }

    @Test
    public void read() {
        VehicleDao vehicleDao = new VehicleDaoImpl();

        vehicleDao.maintain(new Mc.Builder()
                .withBarcode("AAA000")
                .withColour("BLACK")
                .withCustomer(testCustomer)
                .withFuel("GASOLINE")
                .withModel("SUZUKI")
                .withNoiseLevel(5)
                .withNoOfWheels(2)
                .withSize(Vehicle.Size.SMALL)
                .build());

        vehicleDao.maintain(new Car.Builder()
                .withBarcode("BBB000")
                .withColour("BLUE")
                .withCustomer(testCustomer)
                .withFuel("DIESEL")
                .withModel("VOLVO")
                .withNoiseLevel(3)
                .withNoOfWheels(4)
                .withSize(Vehicle.Size.MEDIUM)
                .build());

        Optional <Vehicle> vehicleOpt = vehicleDao.read("AAA000");

        assertTrue (vehicleOpt.isPresent());

        vehicleOpt = vehicleDao.read("BBB000");

        assertTrue (vehicleOpt.isPresent());

        vehicleOpt = vehicleDao.read("CCC000");

        assertFalse (vehicleOpt.isPresent());
    }

    @Test
    public void readAllVehicles() {
        VehicleDao vehicleDao = new VehicleDaoImpl();

        vehicleDao.maintain(new Mc.Builder()
                .withBarcode("AAA000")
                .withColour("BLACK")
                .withCustomer(testCustomer)
                .withFuel("GASOLINE")
                .withModel("SUZUKI")
                .withNoiseLevel(5)
                .withNoOfWheels(2)
                .withSize(Vehicle.Size.SMALL)
                .build());

        vehicleDao.maintain(new Car.Builder()
                .withBarcode("BBB000")
                .withColour("BLUE")
                .withCustomer(testCustomer)
                .withFuel("DIESEL")
                .withModel("VOLVO")
                .withNoiseLevel(3)
                .withNoOfWheels(4)
                .withSize(Vehicle.Size.MEDIUM)
                .build());

        assertTrue (vehicleDao.readAllVehicles().size()==2);
    }

    @Test
    public void printOut() {

        VehicleDao vehicleDao = new VehicleDaoImpl();

        vehicleDao.maintain(new Mc.Builder()
                .withBarcode("AAA000")
                .withColour("BLACK")
                .withCustomer(testCustomer)
                .withFuel("GASOLINE")
                .withModel("SUZUKI")
                .withNoiseLevel(5)
                .withNoOfWheels(2)
                .withSize(Vehicle.Size.SMALL)
                .build());

        vehicleDao.maintain(new Mc.Builder()
                .withBarcode("AAA111")
                .withColour("BLACK")
                .withCustomer(testCustomer)
                .withFuel("GASOLINE")
                .withModel("SUZUKI")
                .withNoiseLevel(5)
                .withNoOfWheels(2)
                .withSize(Vehicle.Size.SMALL)
                .build());

        vehicleDao.maintain(new Car.Builder()
                .withBarcode("BBB555")
                .withColour("BLUE")
                .withCustomer(testCustomer)
                .withFuel("DIESEL")
                .withModel("VOLVO")
                .withNoiseLevel(3)
                .withNoOfWheels(4)
                .withSize(Vehicle.Size.MEDIUM)
                .build());

        vehicleDao.maintain(new Car.Builder()
                .withBarcode("BBB111")
                .withColour("BLUE")
                .withCustomer(testCustomer)
                .withFuel("DIESEL")
                .withModel("VOLVO")
                .withNoiseLevel(3)
                .withNoOfWheels(4)
                .withSize(Vehicle.Size.MEDIUM)
                .build());

        vehicleDao.maintain(new Truck.Builder()
                .withBarcode("CCC666")
                .withColour("BRAUN")
                .withCustomer(testCustomer)
                .withFuel("GASOLINE")
                .withModel("SUZUKI")
                .withNoiseLevel(6)
                .withNoOfWheels(4)
                .withSize(Vehicle.Size.LARGE)
                .build());

        vehicleDao.maintain(new Truck.Builder()
                .withBarcode("CCC111")
                .withColour("RED")
                .withCustomer(testCustomer)
                .withFuel("GASOLINE")
                .withModel("SUZUKI")
                .withNoiseLevel(6)
                .withNoOfWheels(4)
                .withSize(Vehicle.Size.LARGE)
                .build());

        vehicleDao.maintain(new Lorry.Builder()
                .withBarcode("DDD777")
                .withColour("GREEN")
                .withCustomer(testCustomer)
                .withFuel("DIESEL")
                .withModel("VOLVO")
                .withNoiseLevel(3)
                .withNoOfWheels(4)
                .withSize(Vehicle.Size.LARGE)
                .build());

        vehicleDao.maintain(new Lorry.Builder()
                .withBarcode("DDD111")
                .withColour("WHITE")
                .withCustomer(testCustomer)
                .withFuel("DIESEL")
                .withModel("VOLVO")
                .withNoiseLevel(3)
                .withNoOfWheels(4)
                .withSize(Vehicle.Size.LARGE)
                .build());

        vehicleDao.maintain(new Bus.Builder()
                .withBarcode("EEE888")
                .withColour("BLUE")
                .withCustomer(testCustomer)
                .withFuel("DIESEL")
                .withModel("VOLVO")
                .withNoiseLevel(7)
                .withNoOfWheels(6)
                .withSize(Vehicle.Size.LARGE)
                .withNoOfSeats(40)
                .build());

        vehicleDao.maintain(new Bus.Builder()
                .withBarcode("EEE111")
                .withColour("YELLOW")
                .withCustomer(testCustomer)
                .withFuel("DIESEL")
                .withModel("VOLVO")
                .withNoiseLevel(5)
                .withNoOfWheels(8)
                .withSize(Vehicle.Size.LARGE)
                .withNoOfSeats(30)
                .build());

        vehicleDao.maintain(new Juggernaut.Builder()
                .withBarcode("FFF999")
                .withColour("BLUE")
                .withCustomer(testCustomer)
                .withFuel("DIESEL")
                .withModel("VOLVO")
                .withNoiseLevel(7)
                .withNoOfWheels(10)
                .withSize(Vehicle.Size.HUGE)
                .withNoOfBeds(2)
                .build());

        vehicleDao.maintain(new Juggernaut.Builder()
                .withBarcode("FFF111")
                .withColour("BLUE")
                .withCustomer(testCustomer)
                .withFuel("DIESEL")
                .withModel("VOLVO")
                .withNoiseLevel(8)
                .withNoOfWheels(12)
                .withSize(Vehicle.Size.HUGE)
                .withNoOfBeds(1)
                .build());

        vehicleDao.printOut();

    }

}
