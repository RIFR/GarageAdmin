package se.rifr;

import se.rifr.service.GarageService;
import se.rifr.serviceimpl.GarageServiceImpl;

//@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        //SpringApplication.run(Main.class,args);

        GarageService garageService = new GarageServiceImpl();

        garageService.start();
    }
}
