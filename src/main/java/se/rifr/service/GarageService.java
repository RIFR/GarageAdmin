package se.rifr.service;

import se.rifr.domain.Customer;
import se.rifr.domain.Garage;
import se.rifr.domain.Vehicle;

public interface GarageService {

    void LoadReloadData();

    boolean loginOk(String username, String password);

    boolean customerExists(String customerBarcode);

    Customer getCustomer(String barcode);

    void createCustomer(String firstName, String lastName, String barcode, String email, String telno);

    boolean isRegistered(String vehicleBarcode);

    boolean isParked(String vehicleBarcode);

    Vehicle getVehicle(String vehicleBarcode);

    void createVehicle(String regNo, String kind, String model, String colour, Customer customer);

    String parkVehicle (Vehicle vehicle, Garage garage);

    String unparkVehicle(Vehicle vehicle);

    boolean garageExists(String name);

    Garage getGarage(String name);

    void start();
}
