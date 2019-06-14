package se.rifr;

import se.rifr.service.GarageService;
import se.rifr.service.GarageServiceImpl;

public class Main {

    public static void main(String[] args) {

        GarageService garageService = new GarageServiceImpl();

        garageService.start();
    }
}
